package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelRepository {

    private Map<String, Hotel> hotelDb;
    private Map<Integer, User> userDb;
    private Map<String, Booking> bookingDb;

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
        String hotelWithMostFacilities = "";
        int maxFacilities = 0;

        for (Hotel hotel : hotelDb.values()) {
            int numFacilities = hotel.getFacilities().size();
            if (numFacilities > maxFacilities || (numFacilities == maxFacilities && hotel.getHotelName().compareTo(hotelWithMostFacilities) < 0)) {
                maxFacilities = numFacilities;
                hotelWithMostFacilities = hotel.getHotelName();
            }
        }

        return maxFacilities > 0 ? hotelWithMostFacilities : "";
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
        if (!hotelDb.containsKey(hotelName) || newFacilities == null) {
            return null;  // Hotel not found or newFacilities is null
        }

        Hotel hotel = hotelDb.get(hotelName);
        List<Facility> facilities = hotel.getFacilities();

        for (Facility facility : newFacilities) {
            if (!facilities.contains(facility)) {
                facilities.add(facility);
            }
        }

        hotel.setFacilities(facilities);
        hotelDb.put(hotelName, hotel);

        return hotel;
    }

}
