package com.example.RealConnect.properties.domain;

import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @JoinColumn(nullable = false)
    private String customer_id;

    @JoinColumn(nullable = false)
    private String customer_phone;

    @JoinColumn(nullable = false)
    private String owner_name;

    @JoinColumn(nullable = false)
    private String owner_contact;

    @JoinColumn(nullable = false)
    private String address;

    @JoinColumn(nullable = false)
    @Column(precision = 15, scale = 2)
    private BigDecimal price;

    @Column(precision = 15, scale = 2)
    private BigDecimal deposit;

    @Column(precision = 15, scale = 2)
    private BigDecimal monthly_rent;

    @JoinColumn(nullable = false)
    @Column(precision = 10, scale = 2)
    private BigDecimal supply_area;

    @JoinColumn(nullable = false)
    @Column(precision = 10, scale = 2)
    private BigDecimal dedicated_area;

    private Integer floor;

    private String complex_name;

    @JoinColumn(nullable = false)
    private Boolean extension;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @JoinColumn(nullable = false)
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime create_at;

    @Enumerated(EnumType.STRING)
    private Property_type property_type;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Enumerated(EnumType.STRING)
    private Transaction_type transaction_type;

    @Enumerated(EnumType.STRING)
    private Status status;

}
