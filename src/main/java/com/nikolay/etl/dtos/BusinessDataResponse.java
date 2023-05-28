package com.nikolay.etl.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Бизнес данные")
public class BusinessDataResponse {

    private Long id;
    private String type;
    private String businessValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
