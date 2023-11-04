package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class HotelRepository {

    private final Map<String, Hotel> hotelDb;
    private final Map<Integer, User> userDb;
    private final Map<String, Booking> bookingDb;

    public HotelRepository() {
        hotelDb = new HashMap<>();
        userDb = new HashMap<>();
        bookingDb = new HashMap<>();
    }

    public String addHotel(Hotel hotel) {
        if (hotel == null || hotel.getHotelName() == null) {
            return "FAILURE";
        }

        if (hotelDb.containsKey(hotel.getHotelName())) {
            return "FAILURE";
        }

        hotelDb.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        if (user == null) {
            return null;
        }

        int aadharCard = user.getaadharCardNo();
        userDb.put(aadharCard, user);
        return aadharCard;
    }

    public String getHotelWithMostFacilities() {
        Map<String, Integer> hotelFacilityCount = new HashMap<>();

        for (Hotel hotel : hotelDb.values()) {
            int facilityCount = hotel.getFacilities().size();
            hotelFacilityCount.put(hotel.getHotelName(), facilityCount);
        }

        if (hotelFacilityCount.isEmpty()) {
            return "";
        }

        String hotelNameWithMostFacilities = hotelFacilityCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        return hotelNameWithMostFacilities;
    }

    public int bookARoom(Booking booking) {
        if (booking == null || booking.getNoOfRooms() == 0) {
            return -1;
        }

        String hotelName = booking.getHotelName();
        Hotel hotel = hotelDb.get(hotelName);
        if (hotel == null) {
            return -1;
        }

        if (hotel.getAvailableRooms() < booking.getNoOfRooms()) {
            return -1;
        }

        booking.setBookingId(UUID.randomUUID().toString());
        booking.setAmountToBePaid(booking.getNoOfRooms() * hotel.getPricePerNight());
        bookingDb.put(booking.getBookingId(), booking);

        hotel.setAvailableRooms(hotel.getAvailableRooms() - booking.getNoOfRooms());
        hotelDb.put(hotelName, hotel);

        return booking.getAmountToBePaid();
    }

    public List<Booking> getBookings(Integer aadharCard) {
        List<Booking> userBookings = new ArrayList<>();

        for (Booking booking : bookingDb.values()) {
            if (booking.getBookingAadharCard() == aadharCard) {
                userBookings.add(booking);
            }
        }

        return userBookings;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelDb.get(hotelName);
        if (hotel == null) {
            return null;
        }

        List<Facility> existingFacilities = hotel.getFacilities();
        for (Facility newFacility : newFacilities) {
            if (!existingFacilities.contains(newFacility)) {
                existingFacilities.add(newFacility);
            }
        }

        hotel.setFacilities(existingFacilities);
        hotelDb.put(hotelName, hotel);

        return hotel;
    }
}
