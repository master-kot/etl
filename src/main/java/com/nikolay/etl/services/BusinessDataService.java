package com.nikolay.etl.services;

import com.nikolay.etl.daos.BusinessDataRepository;
import com.nikolay.etl.dtos.BusinessDataCreateRequest;
import com.nikolay.etl.dtos.BusinessDataResponse;
import com.nikolay.etl.dtos.BusinessDataUpdateRequest;
import com.nikolay.etl.entities.BusinessData;
import com.nikolay.etl.exceptions.DataNotFoundException;
import com.nikolay.etl.mappers.BusinessDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nikolay.etl.helpers.Constants.DATA_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class BusinessDataService {

    @Value(value = "${spring.kafka.topic-name}")
    private String topicName;

    private final BusinessDataRepository businessDataRepository;
    private final BusinessDataMapper businessDataMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public BusinessDataResponse getById(Long id) {
        return businessDataRepository.findById(id).map(businessDataMapper::mapEntityToDto)
                        .orElseThrow(() -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }

    public BusinessDataResponse save(BusinessDataCreateRequest request) {
        BusinessData data = businessDataRepository.save(businessDataMapper.mapCreateRequestToEntity(request));
        return businessDataMapper.mapEntityToDto(data);
    }

    public void delete(Long id) {
        businessDataRepository.deleteById(id);
    }

    @Transactional
    public void update(BusinessDataUpdateRequest request) {
        BusinessData data = businessDataRepository.findById(request.getId())
                .map(s -> businessDataMapper.updateEntity(s, request))
                .orElse(businessDataMapper.mapUpdateRequestToEntity(request));

        data = businessDataRepository.save(data);
    }
}
