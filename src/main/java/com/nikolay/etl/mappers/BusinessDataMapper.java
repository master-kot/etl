package com.nikolay.etl.mappers;

import com.nikolay.etl.dtos.BusinessDataCreateRequest;
import com.nikolay.etl.dtos.BusinessDataResponse;
import com.nikolay.etl.dtos.BusinessDataUpdateRequest;
import com.nikolay.etl.entities.BusinessData;
import org.mapstruct.*;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static com.nikolay.etl.helpers.Constants.LOCAL_DATE_TIME_PATTERN;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusinessDataMapper {

    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "type", source = "entity.type"),
            @Mapping(target = "businessValue", source = "entity.businessValue"),
            @Mapping(target = "createdAt", source = "entity.createdAt", dateFormat = LOCAL_DATE_TIME_PATTERN),
            @Mapping(target = "updatedAt", source = "entity.updatedAt", dateFormat = LOCAL_DATE_TIME_PATTERN),

    })
    BusinessDataResponse mapEntityToDto(BusinessData entity);

    @Mappings({
            @Mapping(target = "type", source = "request.type"),
            @Mapping(target = "businessValue", source = "request.businessValue"),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
    })
    BusinessData mapCreateRequestToEntity(BusinessDataCreateRequest request);

    @Mappings({
            @Mapping(target = "type", source = "request.type"),
            @Mapping(target = "businessValue", source = "request.businessValue"),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
    })
    BusinessData mapUpdateRequestToEntity(BusinessDataUpdateRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "type", source = "request.type"),
            @Mapping(target = "businessValue", source = "request.businessValue"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
    })
    BusinessData updateEntity(@MappingTarget BusinessData entity, BusinessDataUpdateRequest request);

    List<BusinessDataResponse> mapEntityToDto(List<BusinessData> entities);
}
