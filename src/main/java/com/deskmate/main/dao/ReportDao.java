package com.deskmate.main.dao;

import java.time.LocalDate;
import java.util.Map;

public interface ReportDao {

    // date → total revenue
    Map<LocalDate, Double> getDailyRevenue();

    // deskCode → total bookings
    Map<String, Integer> getDeskUtilization();
}
