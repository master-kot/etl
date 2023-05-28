package com.nikolay.etl.clients;

import com.nikolay.etl.dtos.BusinessDataResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BusinessDataHttpClient {

    public BusinessDataResponse getById(Long id) {
        return new BusinessDataResponse();
    }

    public List<BusinessDataResponse> getAll(int pageNumber, int pageSize) {
        return new ArrayList<>();
    }
}
