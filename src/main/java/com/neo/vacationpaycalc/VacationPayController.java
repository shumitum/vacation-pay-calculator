package com.neo.vacationpaycalc;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "API для расчёта отпускных")
public class VacationPayController {

    private final VacationPayService vacationPayService;
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    @GetMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getVacationPay(@RequestParam(defaultValue = "0") @PositiveOrZero int numberOfVacationDays,
                                     @RequestParam(required = false) @Future
                                     @DateTimeFormat(pattern = DATE_FORMAT) LocalDate startVacationDate,
                                     @RequestParam(required = false) @Future
                                     @DateTimeFormat(pattern = DATE_FORMAT) LocalDate endVacationDate,
                                     @RequestParam @Positive double avgYearlySalary) {
        return vacationPayService.getVacationPay(numberOfVacationDays, startVacationDate, endVacationDate, avgYearlySalary);
    }
}
