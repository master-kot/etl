package com.nikolay.etl.controllers;

import com.nikolay.etl.dtos.BusinessDataCreateRequest;
import com.nikolay.etl.dtos.BusinessDataResponse;
import com.nikolay.etl.dtos.ErrorDto;
import com.nikolay.etl.services.BusinessDataService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nikolay.etl.helpers.Constants.DATA_NOT_FOUND;
import static com.nikolay.etl.helpers.Constants.LOCAL_DATE_TIME_PATTERN;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/data", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@Api(value = "/api/v1/data", tags = {"Контроллер Бизнес данных"})
public class BusinessDataController {

    private final BusinessDataService businessDataService;

    @ApiOperation(value = "Поиск бизнес данных по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BusinessDataResponse> getDataById(@ApiParam("Идентификатор") @PathVariable Long id) {
        return ResponseEntity.ok(businessDataService.getById(id));
    }

    @ApiOperation(value = "Добавление бизнес данных")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно")})
    @PostMapping("")
    public ResponseEntity<BusinessDataResponse> postData(
            @ApiParam(value = "Данные") @RequestBody BusinessDataCreateRequest request) {
        return ResponseEntity.ok(businessDataService.save(request));
    }
}
