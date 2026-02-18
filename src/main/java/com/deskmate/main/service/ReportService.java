package com.deskmate.main.service;

import com.deskmate.main.dao.ReportDao;

import java.time.LocalDate;
import java.util.Map;

public class ReportService {

    private final ReportDao reportDao;

    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public Map<LocalDate, Double> getDailyRevenue() {
        return reportDao.getDailyRevenue();
    }

    public Map<String, Integer> getDeskUtilization() {
        return reportDao.getDeskUtilization();
    }
}
