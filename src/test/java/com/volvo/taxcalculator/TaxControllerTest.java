package com.volvo.taxcalculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volvo.taxcalculator.model.TaxRequest;
import com.volvo.taxcalculator.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TaxRequest taxRequest;

    @BeforeEach
    public void setup() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dateTime1 = LocalDateTime.parse("2013-02-08 18:35:00", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse("2013-03-26 14:25:00", formatter);
        LocalDateTime dateTime3 = LocalDateTime.parse("2013-03-28 14:07:27", formatter);

        LocalDateTime[] dateTimes = {dateTime1, dateTime2, dateTime3};

        taxRequest = new TaxRequest();
        taxRequest.setVehicleType(Vehicle.CAR);
        taxRequest.setDates(dateTimes);
    }

    @Test
    public void testCalculateTax() throws Exception {
        mockMvc.perform(post("/v1/tax/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taxRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Total tax deducted from your rides is: SEK 8"));
    }
}
