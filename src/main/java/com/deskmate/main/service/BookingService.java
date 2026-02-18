package com.deskmate.main.service;

import com.deskmate.main.dao.BookingDao;
import com.deskmate.main.dao.DeskDao;
import com.deskmate.main.dao.PaymentDao;
import com.deskmate.main.enums.*;
import com.deskmate.main.exception.*;
import com.deskmate.main.model.Desk;
import com.deskmate.main.model.Payment;
import com.deskmate.main.utils.DbConnectionFactory;
import com.deskmate.main.utils.ValidationUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;

public class BookingService {

    private static final Logger log =
            LogManager.getLogger(BookingService.class);

    private final DeskDao deskDao;
    private final BookingDao bookingDao;
    private final PaymentDao paymentDao;

    public BookingService(DeskDao deskDao,
                          BookingDao bookingDao,
                          PaymentDao paymentDao) {
        this.deskDao = deskDao;
        this.bookingDao = bookingDao;
        this.paymentDao = paymentDao;
    }

    public long createBookingWithPayment(
            String deskCode,
            String customerPhone,
            LocalDateTime slotStart,
            LocalDateTime slotEnd,
            BigDecimal totalAmount,
            PaymentMode mode,
            BigDecimal paidAmount) {

        ValidationUtil.requireNonBlank(deskCode, "deskCode");
        String phone = ValidationUtil.normalizePhone(customerPhone);

        if (!slotEnd.isAfter(slotStart)) {
            throw new ValidationException(
                    "slotEnd must be after slotStart");
        }

        if (paidAmount.compareTo(totalAmount) != 0) {
            throw new ValidationException("Payment mismatch");
        }

        Desk desk = deskDao.findByCode(deskCode)
                .orElseThrow(() ->
                        new EntityNotFoundException("Desk not found"));

        if (!desk.isActive()) {
            throw new ValidationException("Desk inactive");
        }

        try (Connection conn =
                     DbConnectionFactory.getConnection()) {

            conn.setAutoCommit(false);

            try {

                long bookingId = bookingDao.insertBooking(
                        conn,
                        desk.getDeskId(),
                        phone,
                        slotStart,
                        slotEnd,
                        totalAmount,
                        BookingStatus.CREATED
                );

                Payment payment = new Payment(
                        0,
                        bookingId,
                        mode,
                        paidAmount,
                        PaymentStatus.SUCCESS,
                        LocalDateTime.now()
                );

                paymentDao.insertPayment(conn, payment);

                bookingDao.updateStatus(
                        conn,
                        bookingId,
                        BookingStatus.PAID
                );

                conn.commit();
                log.info("Booking PAID: {}", bookingId);

                return bookingId;

            } catch (Exception e) {
                conn.rollback();
                throw new DatabaseOperationException(
                        "Transaction rolled back", e);
            }

        } catch (Exception e) {
            throw new DatabaseOperationException(
                    "Checkout failed", e);
        }
    }
}
