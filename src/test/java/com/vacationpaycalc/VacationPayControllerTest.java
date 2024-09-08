package com.vacationpaycalc;

import com.vacationpaycalc.service.VacationPayService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VacationPayController.class)
class VacationPayControllerTest {

    private static final String AVG_EARLY_SALARY = "avgYearlySalary";
    private static final String NUMBER_VACATION_DAYS = "numberOfVacationDays";
    private static final String START_VACATION_DATE = "startVacationDate";
    private static final String END_VACATION_DATE = "endVacationDate";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VacationPayService vacationPayService;

    private static Stream<Arguments> provideVacationParameters() {
        return Stream.of(
                Arguments.of(720_000, -1, LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 7)),
                Arguments.of(720_000, 6, LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 7)),
                Arguments.of(0, 6, LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 7))
        );
    }

    @Test
    @SneakyThrows
    void getVacationPay_whenInvokedWithCorrectParams_thenReturnOkStatus() {
        mockMvc.perform(get("/calculate")
                        .param(AVG_EARLY_SALARY, "720000")
                        .param(NUMBER_VACATION_DAYS, "6")
                        .param(START_VACATION_DATE, "01.10.2024")
                        .param(END_VACATION_DATE, "07.10.2024")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(vacationPayService, times(1))
                .getVacationPay(720000,
                        6,
                        LocalDate.of(2024, 10, 1),
                        LocalDate.of(2024, 10, 7));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("provideVacationParameters")
    void getVacationPay_whenInvokedWithWrongNumberOfVacationDays_thenReturnBadRequestStatus(double avgYearlySalary,
                                                                                            int numberOfVacationDays,
                                                                                            LocalDate startVacationDate,
                                                                                            LocalDate endVacationDate) {
        mockMvc.perform(get("/calculate")
                        .param(AVG_EARLY_SALARY, Double.toString(avgYearlySalary))
                        .param(NUMBER_VACATION_DAYS, String.valueOf(numberOfVacationDays))
                        .param(START_VACATION_DATE, startVacationDate.toString())
                        .param(END_VACATION_DATE, endVacationDate.toString())
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());

        verify(vacationPayService, times(0))
                .getVacationPay(avgYearlySalary, numberOfVacationDays, startVacationDate, endVacationDate);
    }
}