package com.nikolay.etl.controllers;

import com.nikolay.etl.services.BusinessDataReplicationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/data", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@Api(value = "/api/v1/data", tags = {"Контроллер Бизнес данных"})
public class BusinessDataController {

    private final BusinessDataReplicationService businessDataReplicationService;

    /**
     * Предлогаю включать репликацию по запросу вместо запланированного включения  по дате через крон
     */
    @ApiOperation(value = "Принудительный запуск репликации данных")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно")})
    @PostMapping("")
    public ResponseEntity<Void> replicateData() {
        businessDataReplicationService.replicateBusinessData();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
