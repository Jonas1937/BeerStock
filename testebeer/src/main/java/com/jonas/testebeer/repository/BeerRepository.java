package com.jonas.testebeer.repository;

import java.util.Optional;

import com.jonas.testebeer.entity.Beer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, String> {
    
    Optional<Beer> findByName(String name);

}
