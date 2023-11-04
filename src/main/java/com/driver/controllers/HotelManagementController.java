package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelManagementController {

    private final HotelService hotelService;

    @Autowired
    public HotelManagementController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/add-hotel")
    public String addHotel(@RequestBody Hotel hotel) {
        return hotelService.addHotel(hotel);
    }

    @PostMapping("/add-user")
    public Integer addUser(@RequestBody User user) {
        return hotelService.addUser(user);
    }

    @GetMapping("/get-hotel-with-most-facilities")
    public String getHotelWithMostFacilities() {
        return hotelService.getHotelWithMostFacilities();
    }

    @PostMapping("/book-a-room")
    public int bookARoom(@RequestBody Booking booking) {
        return hotelService.bookARoom(booking);
    }

    @GetMapping("/get-bookings-by-a-person/{aadharCard}")
    public List<Booking> getBookings(@PathVariable("aadharCard") Integer aadharCard) {
        return hotelService.getBookings(aadharCard);
    }

    @PutMapping("/update-facilities")
    public Hotel updateFacilities(@RequestBody List<Facility> newFacilities, @RequestParam String hotelName) {
        return hotelService.updateFacilities(newFacilities, hotelName);
    }
}
