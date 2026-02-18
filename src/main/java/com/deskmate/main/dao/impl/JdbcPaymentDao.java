package com.deskmate.main.dao.impl;

import com.deskmate.main.dao.PaymentDao;
import com.deskmate.main.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcPaymentDao implements PaymentDao {

    @Override
    public void insertPayment(Connection conn,
                              Payment payment) {

        String sql = """
                INSERT INTO payments
                (booking_id, payment_mode,
                 amount, status, paid_at)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setLong(1, payment.getBookingId());
            ps.setString(2, payment.getMode().name());
            ps.setBigDecimal(3, payment.getAmount());
            ps.setString(4, payment.getStatus().name());
            ps.setTimestamp(5,
                    java.sql.Timestamp.valueOf(
                            payment.getPaidAt()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
