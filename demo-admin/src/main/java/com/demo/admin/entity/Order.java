package com.demo.admin.entity;

import javax.persistence.Column;
import java.sql.Timestamp;

public class Order {
    @Column(nullable = false)
    private Timestamp tx_creation_time;
    @Column(nullable = false)
    private Timestamp tx_modification_time;
}
