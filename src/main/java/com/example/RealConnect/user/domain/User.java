package com.example.RealConnect.user.domain;

import com.example.RealConnect.inquiry.domain.Inquiry;
import com.example.RealConnect.property.domain.Property;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    /*
        문의 고객 리스트
     */
    @OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
    private List<Inquiry> inquiries;

    /*
        매물 리스트
     */
    @OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
    private List<Property> properties;
}
