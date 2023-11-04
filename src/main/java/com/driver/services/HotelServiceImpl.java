package com.driver.services;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.driver.services.HotelService;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public String addHotel(Hotel hotel) {
        return hotelRepository.addHotel(hotel);
    }

    @Override
    public Integer addUser(User user) {
        return hotelRepository.addUser(user);
    }

    @Override
    public String getHotelWithMostFacilities() {
        return hotelRepository.getHotelWithMostFacilities();
    }

    @Override
    public int bookARoom(Booking booking) {
        return hotelRepository.bookARoom(booking);
    }

    @Override
    public List<Booking> getBookings(Integer aadharCard) {
        return hotelRepository.getBookings(aadharCard);
    }

    @Override
    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelRepository.updateFacilities(newFacilities, hotelName);
    }
}
