package com.nikolay.etl.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Информация об ошибке")
public class ErrorDto {

    @ApiModelProperty(notes = "Сообщение об ошибке", dataType = "String", required = true)
    private final String message;
}
