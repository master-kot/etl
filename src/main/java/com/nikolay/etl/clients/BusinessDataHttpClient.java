package com.nikolay.etl.clients;

import com.nikolay.etl.dtos.BusinessDataResponse;
import com.nikolay.etl.exceptions.BadRequestException;
import com.nikolay.etl.exceptions.CommonNotFoundException;
import com.nikolay.etl.exceptions.RetryableResponseStatusException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BusinessDataHttpClient {

    @Value(value = "${business.data.server.address}")
    private String dataServerAddress;

    private static final String GET_BY_ID_URL = "%s/api/v1/data/%s";
    private static final String GET_ALL_URL = "%s/api/v1/data?pageNumber=%d,pageSize=%d";
    private static final Set<HttpStatusCode> RETRYABLE_HTTP_STATUSES = Set.of(
            HttpStatus.GATEWAY_TIMEOUT,
            HttpStatus.BAD_GATEWAY,
            HttpStatus.SERVICE_UNAVAILABLE,
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.UNAUTHORIZED
    );
    private static final MultiValueMap<String, String> EMPTY_QUERY_PARAMS = new LinkedMultiValueMap<>();

    private final WebClient webClient;

    public BusinessDataResponse getById(Long id) {
        return invoke(HttpMethod.GET,
                String.format(GET_BY_ID_URL, dataServerAddress, id),
                "",
                EMPTY_QUERY_PARAMS,
                ParameterizedTypeReference.forType(BusinessDataResponse.class));
    }

    public List<BusinessDataResponse> getAll(int pageNumber, int pageSize) {
        ParameterizedTypeReference<List<BusinessDataResponse>> returnType = new ParameterizedTypeReference<>() {};
        return invoke(HttpMethod.GET,
                String.format(GET_ALL_URL, dataServerAddress, pageNumber, pageSize),
                "",
                EMPTY_QUERY_PARAMS,
                returnType);
    }

    private <T> T invoke(HttpMethod method, String path, Object body, MultiValueMap<String, String> queryParams,
                         ParameterizedTypeReference<T> returnType) {
        return webClient
                .method(method)
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParams(queryParams)
                        .build())
                .body(BodyInserters.fromValue(body))
                .exchange()
                .flatMap(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        return clientResponse.createException().flatMap(Mono::error);
                    } else {
                        return clientResponse.bodyToMono(returnType);
                    }
                })
                .doOnError(ex -> {
                    String errorMessage = null;
                    if (ex instanceof WebClientResponseException) {
                        errorMessage = ((WebClientResponseException) ex).getResponseBodyAsString();
                        HttpStatusCode statusCode = ((WebClientResponseException) ex).getStatusCode();
                        if (HttpStatus.NOT_FOUND.equals(statusCode)) {
                            throw new CommonNotFoundException(errorMessage);
                        }
                        if (HttpStatus.BAD_REQUEST.equals(statusCode)) {
                            throw new BadRequestException(errorMessage);
                        }
                        if (RETRYABLE_HTTP_STATUSES.contains(statusCode)) {
                            throw new RetryableResponseStatusException(statusCode, errorMessage);
                        }
                    }
                    throw new RuntimeException(StringUtils.isNotBlank(errorMessage) ? errorMessage : ex.getMessage());
                })
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> throwable instanceof RetryableResponseStatusException))
                .block();
    }
}
