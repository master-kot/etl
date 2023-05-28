package com.nikolay.etl.mappers;

import com.nikolay.etl.dtos.BusinessDataResponse;
import com.nikolay.etl.entities.BusinessData;
import org.mapstruct.*;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusinessDataMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "type", source = "dto.type"),
            @Mapping(target = "businessValue", source = "dto.businessValue"),
            @Mapping(target = "createdAt", source = "dto.createdAt"),
            @Mapping(target = "updatedAt", source = "dto.updatedAt"),
    })
    BusinessData updateEntity(@MappingTarget BusinessData entity, BusinessDataResponse dto);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "type", source = "dto.type"),
            @Mapping(target = "businessValue", source = "dto.businessValue"),
            @Mapping(target = "createdAt", source = "dto.createdAt"),
            @Mapping(target = "updatedAt", source = "dto.updatedAt"),

    })
    BusinessData mapResponseToEntity(BusinessDataResponse dto);

    List<BusinessData> mapResponseToEntity(List<BusinessDataResponse> responses);
}
