package com.example.RealConnect.customer.domain;

import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User agent;

    private String name;

    private String phone;

    @OneToMany(mappedBy = "owner")
    private List<Property> owning;

    @OneToMany(mappedBy = "tenant")
    private List<Property> tenanting;

//    private String memo;
}
