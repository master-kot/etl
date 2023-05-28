package com.nikolay.etl.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос изменения данных")
public class BusinessDataUpdateRequest extends BusinessDataCreateRequest {

    @ApiModelProperty(notes = "Идентификатор", dataType = "Long", required = true)
    private Long id;
}
