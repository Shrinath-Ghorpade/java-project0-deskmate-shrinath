package com.deskmate.main;

import com.deskmate.main.controller.BookingController;
import com.deskmate.main.controller.DeskController;
import com.deskmate.main.controller.ReportController;
import com.deskmate.main.dao.impl.JdbcBookingDao;
import com.deskmate.main.dao.impl.JdbcDeskDao;
import com.deskmate.main.dao.impl.JdbcPaymentDao;
import com.deskmate.main.dao.impl.JdbcReportDao;
import com.deskmate.main.service.BookingService;
import com.deskmate.main.service.DeskService;
import com.deskmate.main.service.ReportService;

public class AppConfig {

    public DeskController deskController() {
        DeskDao deskDao = new JdbcDeskDao();
        DeskService deskService = new DeskService(deskDao);
        return new DeskController(deskService);
    }

    public BookingController bookingController() {
        DeskDao deskDao = new JdbcDeskDao();
        BookingDao bookingDao = new JdbcBookingDao();
        PaymentDao paymentDao = new JdbcPaymentDao();
        BookingService bookingService = new BookingService(deskDao, bookingDao, paymentDao);
        return new BookingController(bookingService);
    }

    public ReportController reportController() {
        ReportDao reportDao = new JdbcReportDao();
        ReportService reportService = new ReportService(reportDao);
        return new ReportController(reportService);
    }

}
