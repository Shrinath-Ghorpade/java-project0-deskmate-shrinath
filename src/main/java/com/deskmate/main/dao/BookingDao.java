package com.deskmate.main.dao;

import com.deskmate.main.enums.BookingStatus;
import com.deskmate.main.model.Booking;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingDao {

    long insertBooking(Connection conn,
                       long deskId,
                       String phone,
                       LocalDateTime start,
                       LocalDateTime end,
                       BigDecimal total,
                       BookingStatus status);

    void updateStatus(Connection conn,
                      long bookingId,
                      BookingStatus status);

    Optional<Booking> findById(long bookingId);
}

