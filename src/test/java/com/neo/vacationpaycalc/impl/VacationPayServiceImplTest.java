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
        endVacationDate = LocalDate.of(2024, 6, 6);
        avgYearlySalary = 720_000;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getVacationPay() {
        BigDecimal vacationPay = vacationPayService.getVacationPay(numberOfVacationDays, null, null, avgYearlySalary);
        assertEquals(BigDecimal.valueOf(10689.42), vacationPay);
    }

    @Test
    void getVacationPay_when() {
        assertThrows(NotEnoughDataException.class, () -> vacationPayService
                .getVacationPay(0, null, null, 0));
    }

    @Test
    void getVacationPay_whenVacationStartEqualsEnd() {
        assertThrows(DateValidationException.class, () -> vacationPayService
                .getVacationPay(0, startVacationDate, startVacationDate, 0));
    }

    @Test
    void getVacationPay_whenInv() {
        assertThrows(DateValidationException.class, () -> vacationPayService
                .getVacationPay(0, endVacationDate, startVacationDate, 0));
    }

    @Test
    void getVacationPay_whenInvokeWithNotEnoughData() {
        assertThrows(NotEnoughDataException.class, () -> vacationPayService
                .getVacationPay(0, startVacationDate, null, avgYearlySalary));
    }
}