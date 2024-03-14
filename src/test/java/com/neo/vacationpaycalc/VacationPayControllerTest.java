package com.neo.vacationpaycalc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VacationPayController.class)
class VacationPayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VacationPayService vacationPayService;

    @Test
    @SneakyThrows
    void getVacationPay_whenInvokedWithCorrectParams_thenReturnOkStatus() {
        mockMvc.perform(get("/calculate")
                        .param("numberOfVacationDays", "6")
                        .param("startVacationDate", "01.06.2024")
                        .param("endVacationDate", "07.06.2024")
                        .param("avgYearlySalary", "720000")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(vacationPayService, times(1))
                .getVacationPay(6,
                        LocalDate.of(2024, 6, 1),
                        LocalDate.of(2024, 6, 7),
                        720000);
    }

    @Test
    @SneakyThrows
    void getVacationPay_whenInvokedWithWrongNumberOfVacationDays_thenReturnBadRequestStatus() {
        mockMvc.perform(get("/calculate")
                        .param("numberOfVacationDays", "-1")
                        .param("startVacationDate", "01.06.2024")
                        .param("endVacationDate", "07.06.2024")
                        .param("avgYearlySalary", "720000")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());

        verify(vacationPayService, times(0))
                .getVacationPay(-1,
                        LocalDate.of(2024, 6, 1),
                        LocalDate.of(2024, 6, 7),
                        720000);
    }

    @Test
    @SneakyThrows
    void getVacationPay_whenInvokedWithWrongDates_thenReturnBadRequestStatus() {
        mockMvc.perform(get("/calculate")
                        .param("numberOfVacationDays", "6")
                        .param("startVacationDate", "01.06.2023")
                        .param("endVacationDate", "07.06.2023")
                        .param("avgYearlySalary", "720000")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());

        verify(vacationPayService, times(0))
                .getVacationPay(6,
                        LocalDate.of(2024, 6, 1),
                        LocalDate.of(2024, 6, 7),
                        720000);
    }

    @Test
    @SneakyThrows
    void getVacationPay_whenInvokedWithWrongAvgSalary_thenReturnBadRequestStatus() {
        mockMvc.perform(get("/calculate")
                        .param("numberOfVacationDays", "6")
                        .param("startVacationDate", "01.06.2024")
                        .param("endVacationDate", "07.06.2024")
                        .param("avgYearlySalary", "0")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());

        verify(vacationPayService, times(0))
                .getVacationPay(6,
                        LocalDate.of(2024, 6, 1),
                        LocalDate.of(2024, 6, 7),
                        0);
    }
}