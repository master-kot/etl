package com.nikolay.etl.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventMessageDto {

    private Long id;
    private String action;
    private Long eventTime;
}
