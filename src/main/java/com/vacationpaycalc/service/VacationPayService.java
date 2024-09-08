package com.vacationpaycalc.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VacationPayService {
    BigDecimal getVacationPay(double avgYearlySalary,
                              int numberOfVacationDays,
                              LocalDate startVacationDate,
                              LocalDate endVacationDate);
}
