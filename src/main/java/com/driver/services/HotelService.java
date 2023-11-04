package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.List;

public interface HotelService {

    String addHotel(Hotel hotel);

    Integer addUser(User user);

    String getHotelWithMostFacilities();

    int bookARoom(Booking booking);

    List<Booking> getBookings(Integer aadharCard);

    Hotel updateFacilities(List<Facility> newFacilities, String hotelName);
}
