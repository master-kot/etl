package com.nikolay.etl.services;

import com.nikolay.etl.clients.BusinessDataHttpClient;
import com.nikolay.etl.dtos.BusinessDataResponse;
import com.nikolay.etl.dtos.EventMessageDto;
import com.nikolay.etl.enums.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessDataReplicationService {

    @Value(value = "${service.replication.page-size}")
    private Integer pageSize;

    private final BusinessDataService businessDataService;
    private final BusinessDataHttpClient httpClient;

    /**
     * В условиях ничего не сказано про сохранение событий, поэтому не сохраняем, а только работаем с ними
     */
    @KafkaListener(topics = "data", groupId = "data", containerFactory = "kafkaListenerContainerFactory")
    public void processEventMessage(EventMessageDto event) {
        BusinessDataResponse response = httpClient.getById(event.getId());
        switch (EventType.valueOf(event.getAction())) {
            case CREATE -> businessDataService.save(response);
            case UPDATE -> businessDataService.update(response);
            case DELETE -> businessDataService.delete(response.getId());
        }
    }

    /**
     * Предлогаю включать репликацию по запросу через Async, чтобы клиент не ждал ответа,
     * вместо даты запланированного включения, фиксированно указанной в анотации Scheduled(cron="...")
     */
    @Async
    public void replicateBusinessData() {
        int pageNumber = 0;
        List<BusinessDataResponse> responseList;
        do {
            responseList = httpClient.getAll(pageNumber++, pageSize);
            businessDataService.upsert(responseList);
        } while (!responseList.isEmpty());
    }
}
