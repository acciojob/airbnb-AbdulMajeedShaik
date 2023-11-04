package com.driver.test;

import com.driver.controllers.HotelManagementController;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.services.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TestCases {

    private MockMvc mockMvc;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private HotelManagementController hotelManagementController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(hotelManagementController)
                .build();
    }

    @Test
    public void testGetBookingsByPerson() throws Exception {
        List<Booking> bookings = new ArrayList<>();
        Booking booking = new Booking("1", 123456, 2, "John", "Hotel A");
        bookings.add(booking);
        when(hotelService.getBookings(123456)).thenReturn(bookings);

        mockMvc.perform(get("/hotel/get-bookings-by-a-person/123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(hotelService, times(1)).getBookings(123456);
    }
}
