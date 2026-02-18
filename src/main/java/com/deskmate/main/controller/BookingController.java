package com.deskmate.main.controller;

import com.deskmate.main.enums.PaymentMode;
import com.deskmate.main.service.BookingService;
import com.deskmate.main.utils.DateUtil;
import com.deskmate.main.utils.InputUtil;
import com.deskmate.main.utils.MoneyUtil;

import java.time.LocalDateTime;

public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void menu() {
        while (true) {
            System.out.println("\n--- Booking & Payment ---");
            System.out.println("1. Create booking (with payment)");
            System.out.println("0. Back");

            int c = InputUtil.readInt("Choose: ");

            switch (c) {
                case 1 -> createBooking();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void createBooking() {

        String deskCode =
                InputUtil.readString("Desk code: ");

        String phone =
                InputUtil.readString("Customer phone: ");

        LocalDateTime start =
                DateUtil.parseDateTime(
                        InputUtil.readString(
                                "slotStart (yyyy-MM-dd HH:mm): "));

        LocalDateTime end =
                DateUtil.parseDateTime(
                        InputUtil.readString(
                                "slotEnd (yyyy-MM-dd HH:mm): "));

        var total = MoneyUtil.parse(
                InputUtil.readString("totalAmount: "));

        System.out.println("1) CASH  2) CARD  3) UPI");

        int pm = InputUtil.readInt("Choose payment mode: ");

        PaymentMode mode = switch (pm) {
            case 1 -> PaymentMode.CASH;
            case 2 -> PaymentMode.CARD;
            case 3 -> PaymentMode.UPI;
            default -> throw new IllegalArgumentException("Invalid mode");
        };

        var paid = MoneyUtil.parse(
                InputUtil.readString("paidAmount: "));

        long id = bookingService.createBookingWithPayment(
                deskCode,
                phone,
                start,
                end,
                total,
                mode,
                paid
        );

        System.out.println("Booking created successfully. ID = " + id);
    }
}
