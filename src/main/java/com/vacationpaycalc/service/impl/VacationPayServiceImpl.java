package com.vacationpaycalc.service.impl;

import com.vacationpaycalc.exception.DateValidationException;
import com.vacationpaycalc.exception.NotEnoughDataException;
import com.vacationpaycalc.service.CalendarService;
import com.vacationpaycalc.service.VacationPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationPayServiceImpl implements VacationPayService {

    private final CalendarService calendarService;

    private static final double NDFL = 0.13;
    private static final double AVG_DAYS_IN_MONTH = 29.3;

    @Override
    public BigDecimal getVacationPay(double avgYearlySalary,
                                     int numberOfVacationDays,
                                     LocalDate startVacationDate,
                                     LocalDate endVacationDate) {
        if (startVacationDate != null && endVacationDate != null) {
            validateVacationDates(startVacationDate, endVacationDate);
            log.info("Расчёт отпускных за период с {} по {}, средняя заработная плата за 12 мес. {} руб.",
                    startVacationDate, endVacationDate, avgYearlySalary);
            return calculateVacationPayByDates(startVacationDate, endVacationDate, avgYearlySalary);
        } else if (numberOfVacationDays != 0) {
            log.info("Расчёт отпускных: количество дней отпуска - {}, средняя заработная плата за 12 мес. {} руб.",
                    numberOfVacationDays, avgYearlySalary);
            return calculateVacationPayByDays(numberOfVacationDays, avgYearlySalary);
        } else {
            throw new NotEnoughDataException("Недостаточно данных для расчёта");
        }
    }

    private BigDecimal calculateVacationPayByDays(int numberOfVacationDays, double avgYearlySalary) {
        return BigDecimal.valueOf(avgYearlySalary / (12 * AVG_DAYS_IN_MONTH) * numberOfVacationDays * (1 - NDFL))
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateVacationPayByDates(LocalDate startVacationDate,
                                                   LocalDate endVacationDate,
                                                   double avgMonthlySalary) {
        final int numberOfPaidVacationDays;
        numberOfPaidVacationDays = getNumberOfPaidVacationDays(startVacationDate, endVacationDate);
        return calculateVacationPayByDays(numberOfPaidVacationDays, avgMonthlySalary);
    }

    private int getNumberOfPaidVacationDays(LocalDate startVacationDate, LocalDate endVacationDate) {
        int[] officialHolidays = calendarService.getHolidays();
        int numberOfPaidVacationDays;
        numberOfPaidVacationDays = Period.between(startVacationDate, endVacationDate).getDays() + 1;
        for (int day : officialHolidays) {
            if (startVacationDate.getDayOfYear() <= day && endVacationDate.getDayOfYear() >= day) {
                --numberOfPaidVacationDays;
            }
        }
        return numberOfPaidVacationDays;
    }

    private void validateVacationDates(LocalDate startVacationDate, LocalDate endVacationDate) {
        if (endVacationDate.isBefore(startVacationDate)) {
            throw new DateValidationException("Последний день отпуска не должен наступать раньше, чем первый день отпуска");
        }
        if (startVacationDate.equals(endVacationDate)) {
            throw new DateValidationException("Даты начала и окончания отпуска не должны совпадать");
        }
    }
}