package com.example.RealConnect.apartment.repository;

import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.property.domain.dto.ApartmentPropertyPage;
import com.example.RealConnect.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    @Query(value= """
        select 
            a.id            as apartmentId,
            a.name          as apartmentName,
            a.Dong          as dong,
            a.Ho            as ho,
            a.area          as area,
            a.type          as type,
            a.direction     as direction,
            a.img           as img,
            
            p               as property
        from
            Apartment a
            left join Property p
            on p.apartment = a
            and p.agent = :agent
        order by a.id
    """,
    countQuery = "select count(a) from Apartment a")
    Page<ApartmentPropertyPage> findPageWithMyProperty(@Param("agent") User user, Pageable pageable);

}
