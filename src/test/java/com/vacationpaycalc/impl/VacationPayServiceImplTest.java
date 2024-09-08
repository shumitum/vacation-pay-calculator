package com.vacationpaycalc.impl;

import com.vacationpaycalc.exception.DateValidationException;
import com.vacationpaycalc.exception.NotEnoughDataException;
import com.vacationpaycalc.service.VacationPayService;
import com.vacationpaycalc.service.impl.CalendarServiceImpl;
import com.vacationpaycalc.service.impl.VacationPayServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VacationPayServiceImplTest {

    private final VacationPayService vacationPayService = new VacationPayServiceImpl(new CalendarServiceImpl());

    private static final double AVG_EARLY_SALARY = 720_000;
    private static final int NUMBER_VACATION_DAYS = 6;
    private static final LocalDate START_VACATION_DATE = LocalDate.of(2024, 10, 28);
    private static final LocalDate END_VACATION_DATE = LocalDate.of(2024, 11, 3);

    private static Stream<Arguments> provideVacationParameters() {
        return Stream.of(
                Arguments.of(AVG_EARLY_SALARY, NUMBER_VACATION_DAYS, null, null, BigDecimal.valueOf(10_689.42)),
                Arguments.of(AVG_EARLY_SALARY, 0, START_VACATION_DATE, END_VACATION_DATE, BigDecimal.valueOf(12_470.99)),
                Arguments.of(AVG_EARLY_SALARY, 0, START_VACATION_DATE,
                        LocalDate.of(2024, 11, 8), BigDecimal.valueOf(19_597.27))
        );
    }

    private static Stream<Arguments> provideWrongVacationParameters() {
        return Stream.of(
                Arguments.of(AVG_EARLY_SALARY, 0, null, null),
                Arguments.of(AVG_EARLY_SALARY, 0, START_VACATION_DATE, null),
                Arguments.of(AVG_EARLY_SALARY, 0, null, END_VACATION_DATE)
        );
    }

    @ParameterizedTest
    @MethodSource("provideVacationParameters")
    void getVacationPay_whenInvoked_thenReturnCorrectVacationPay(double avgYearlySalary,
                                                                 int numberOfVacationDays,
                                                                 LocalDate startVacationDate,
                                                                 LocalDate endVacationDate,
                                                                 BigDecimal result) {
        BigDecimal vacationPay = vacationPayService
                .getVacationPay(avgYearlySalary, numberOfVacationDays, startVacationDate, endVacationDate);

        assertEquals(result, vacationPay);
    }

    @ParameterizedTest
    @MethodSource("provideWrongVacationParameters")
    void getVacationPay_whenInvokedWithWrongParameters_thenThrowNotEnoughDataException(double avgYearlySalary,
                                                                                       int numberOfVacationDays,
                                                                                       LocalDate startVacationDate,
                                                                                       LocalDate endVacationDate) {
        assertThrows(NotEnoughDataException.class, () -> vacationPayService
                .getVacationPay(avgYearlySalary, numberOfVacationDays, startVacationDate, endVacationDate));
    }

    @Test
    void getVacationPay_whenInvokedVacationStartDateEqualsEndDate_thenThrowDateValidationException() {
        assertThrows(DateValidationException.class, () -> vacationPayService
                .getVacationPay(0, 0, START_VACATION_DATE, START_VACATION_DATE));
    }

    @Test
    void getVacationPay_whenInvokedWithEndDateBeforeStartDate_thenThrowDateValidationException() {
        assertThrows(DateValidationException.class, () -> vacationPayService
                .getVacationPay(0, 0, END_VACATION_DATE, START_VACATION_DATE));
    }
}