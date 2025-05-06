package com.example.RealConnect.apartment.repository;

import com.example.RealConnect.apartment.domain.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

}
