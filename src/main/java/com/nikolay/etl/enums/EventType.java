package com.nikolay.etl.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventType {

    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String type;
}
