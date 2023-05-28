package com.nikolay.etl.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto {

    private Long id;
    private String action;
    private Long eventTime;
}
