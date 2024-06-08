package com.example.h2plusWeb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByStatus(FlightStatus status);

    @Query("SELECT f FROM Flight f WHERE f.status = :p1 OR f.status = :p2")
    List<Flight> findByStatuses(FlightStatus p1, FlightStatus p2);
}
