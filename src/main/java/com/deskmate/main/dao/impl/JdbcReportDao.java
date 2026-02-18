package com.deskmate.main.dao.impl;

import com.deskmate.main.dao.ReportDao;
import com.deskmate.main.utils.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class JdbcReportDao implements ReportDao {

    @Override
    public Map<LocalDate, Double> getDailyRevenue() {

        String sql = """
                SELECT DATE(paid_at) as day,
                       SUM(amount) as total
                FROM payments
                WHERE status = 'SUCCESS'
                GROUP BY DATE(paid_at)
                ORDER BY day
                """;

        Map<LocalDate, Double> result = new LinkedHashMap<>();

        try (Connection conn = DbConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.put(
                        rs.getDate("day").toLocalDate(),
                        rs.getDouble("total")
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Map<String, Integer> getDeskUtilization() {

        String sql = """
                SELECT d.desk_code,
                       COUNT(b.booking_id) as total_bookings
                FROM desks d
                LEFT JOIN bookings b
                    ON d.desk_id = b.desk_id
                GROUP BY d.desk_code
                ORDER BY total_bookings DESC
                """;

        Map<String, Integer> result = new LinkedHashMap<>();

        try (Connection conn = DbConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.put(
                        rs.getString("desk_code"),
                        rs.getInt("total_bookings")
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
