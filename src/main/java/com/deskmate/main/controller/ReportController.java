package com.deskmate.main.controller;

import com.deskmate.main.service.ReportService;
import com.deskmate.main.utils.InputUtil;

import java.time.LocalDate;
import java.util.Map;

public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    public void menu() {
        while (true) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Daily Revenue");
            System.out.println("2. Desk Utilization");
            System.out.println("0. Back");

            int c = InputUtil.readInt("Choose: ");

            switch (c) {
                case 1 -> showDailyRevenue();
                case 2 -> showDeskUtilization();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void showDailyRevenue() {
        Map<LocalDate, Double> data =
                reportService.getDailyRevenue();

        System.out.println("\n--- Daily Revenue ---");
        data.forEach((date, total) ->
                System.out.println(date + " | ₹" + total));
    }

    private void showDeskUtilization() {
        Map<String, Integer> data =
                reportService.getDeskUtilization();

        System.out.println("\n--- Desk Utilization ---");
        data.forEach((desk, count) ->
                System.out.println(desk + " | Bookings: " + count));
    }
}
