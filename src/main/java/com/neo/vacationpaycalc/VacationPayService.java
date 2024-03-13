package com.neo.vacationpaycalc;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VacationPayService {
    BigDecimal getVacationPay(int numberOfVacationDays,
                              LocalDate startVacationDate,
                              LocalDate endVacationDate,
                              double avgYearlySalary);
}
