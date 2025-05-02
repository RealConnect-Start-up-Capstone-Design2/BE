package com.example.RealConnect.properties.repository;

import com.example.RealConnect.properties.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {

}
