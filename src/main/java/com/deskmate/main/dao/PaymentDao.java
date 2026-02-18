package com.deskmate.main.dao;

import com.deskmate.main.model.Payment;

import java.sql.Connection;

public interface PaymentDao {
    void insertPayment(Connection conn, Payment payment);
}
