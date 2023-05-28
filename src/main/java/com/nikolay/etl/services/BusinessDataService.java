package com.nikolay.etl.services;

import com.nikolay.etl.daos.BusinessDataRepository;
import com.nikolay.etl.dtos.BusinessDataResponse;
import com.nikolay.etl.entities.BusinessData;
import com.nikolay.etl.mappers.BusinessDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessDataService {

    private final BusinessDataRepository businessDataRepository;
    private final BusinessDataMapper businessDataMapper;

    public void save(BusinessDataResponse response) {
        businessDataRepository.save(businessDataMapper.mapResponseToEntity(response));
    }

    @Transactional
    public void update(BusinessDataResponse response) {
        BusinessData data = businessDataRepository.findById(response.getId())
                .map(s -> businessDataMapper.updateEntity(s, response))
                .orElse(businessDataMapper.mapResponseToEntity(response));
        businessDataRepository.save(data);
    }

    @Transactional
    public void upsert(List<BusinessDataResponse> responseList) {
        Set<Long> ids = responseList.stream().map(BusinessDataResponse::getId).collect(Collectors.toSet());
        Map<Long, BusinessData> dataToUpdate = businessDataRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(BusinessData::getId, s -> s));
        List<BusinessData> dataToInsert = new ArrayList<>();
        for (BusinessDataResponse response : responseList) {
            BusinessData data = dataToUpdate.get(response.getId());
            if (data != null) {
                businessDataMapper.updateEntity(data, response);
            } else {
                dataToInsert.add(businessDataMapper.mapResponseToEntity(response));
            }
        }
        businessDataRepository.saveAll(dataToUpdate.values());
        businessDataRepository.saveAll(dataToInsert);
    }

    public void delete(Long id) {
        businessDataRepository.deleteById(id);
    }
}
