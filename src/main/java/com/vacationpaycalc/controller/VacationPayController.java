package com.vacationpaycalc.controller;

import com.vacationpaycalc.service.VacationPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Запрос на расчёт отпускных",
            description = "Эндпоинт принимает среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает " +
                    "суммой отпускных, если в запросе указаны даты начала и окончания отпуска (в формате дд.мм.гггг)," +
                    " то параметр количество дней отпуска игнорируется ")
    @GetMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getVacationPay(@Parameter(description = "средняя зарплата за 12 месяцев")
                                     @RequestParam @Positive double avgYearlySalary,
                                     @Parameter(description = "Количество дней отпуска")
                                     @RequestParam(defaultValue = "0") @PositiveOrZero int numberOfVacationDays,
                                     @Parameter(description = "Дата начала отпуска в формате дд.мм.гггг")
                                     @RequestParam(required = false) @Future
                                     @DateTimeFormat(pattern = DATE_FORMAT) LocalDate startVacationDate,
                                     @Parameter(description = "Дата окончания отпуска в формате дд.мм.гггг")
                                     @RequestParam(required = false) @Future
                                     @DateTimeFormat(pattern = DATE_FORMAT) LocalDate endVacationDate) {
        return vacationPayService.getVacationPay(avgYearlySalary, numberOfVacationDays, startVacationDate, endVacationDate);
    }
}
