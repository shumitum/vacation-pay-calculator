package com.neo.vacationpaycalc.impl;

import com.neo.vacationpaycalc.VacationPayService;
import com.neo.vacationpaycalc.exception.DateValidationException;
import com.neo.vacationpaycalc.exception.NotEnoughDataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VacationPayServiceImplTest {

    private VacationPayService vacationPayService;

    int numberOfVacationDays;
    LocalDate startVacationDate;
    LocalDate endVacationDate;
    double avgYearlySalary;

    @BeforeEach
    void setUp() {
        vacationPayService = new VacationPayServiceImpl();
        numberOfVacationDays = 6;
        startVacationDate = LocalDate.of(2024, 6, 1);
        endVacationDate = LocalDate.of(2024, 6, 7);
        avgYearlySalary = 720_000;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getVacationPay_whenInvokedWithDaysAndSalary_thenReturnCorrectVacationPay() {
        BigDecimal vacationPay = vacationPayService.getVacationPay(numberOfVacationDays, null, null, avgYearlySalary);

        assertEquals(BigDecimal.valueOf(10_689.42), vacationPay);
    }

    @Test
    void getVacationPay_whenInvokedWithDatesAndSalary_thenReturnCorrectVacationPay() {
        BigDecimal vacationPay = vacationPayService.getVacationPay(0, startVacationDate, endVacationDate, avgYearlySalary);

        assertEquals(BigDecimal.valueOf(12_470.99), vacationPay);
    }

    @Test
    void getVacationPay_getVacationPay_whenInvokedVacationIncludeHoliday_thenReturnCorrectVacationPay() {
        BigDecimal vacationPay = vacationPayService.getVacationPay(0, startVacationDate,
                LocalDate.of(2024, 6, 12), avgYearlySalary);

        assertEquals(BigDecimal.valueOf(19_597.27), vacationPay);
    }

    @Test
    void getVacationPay_whenInvokedWithDaysAndDates_thenThrowNotEnoughDataException() {
        assertThrows(NotEnoughDataException.class, () -> vacationPayService
                .getVacationPay(0, null, null, avgYearlySalary));
    }

    @Test
    void getVacationPay_whenInvokedVacationStartDateEqualsEndDate_thenThrowDateValidationException() {
        assertThrows(DateValidationException.class, () -> vacationPayService
                .getVacationPay(0, startVacationDate, startVacationDate, 0));
    }

    @Test
    void getVacationPay_whenInvokedWithEndDateBeforeStartDate_thenThrowDateValidationException() {
        assertThrows(DateValidationException.class, () -> vacationPayService
                .getVacationPay(0, endVacationDate, startVacationDate, 0));
    }

    @Test
    void getVacationPay_whenInvokeWithNoEndDate_thenThrowNotEnoughDataException() {
        assertThrows(NotEnoughDataException.class, () -> vacationPayService
                .getVacationPay(0, startVacationDate, null, avgYearlySalary));
    }

    @Test
    void getVacationPay_whenInvokeWithNoStartDate_thenThrowNotEnoughDataException() {
        assertThrows(NotEnoughDataException.class, () -> vacationPayService
                .getVacationPay(0, null, endVacationDate, avgYearlySalary));
    }
}