package com.example.RealConnect.property.repository;

import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.user.domain.User;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @NonNull
    List<Property> findAll();

    List<Property> findAllByAgent(User agent);

    boolean existsByApartmentAndAgent(Apartment apartment, User agent);
}
