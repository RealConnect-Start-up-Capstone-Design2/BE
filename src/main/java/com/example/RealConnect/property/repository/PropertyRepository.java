package com.example.RealConnect.property.repository;

import com.example.RealConnect.property.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {

}
