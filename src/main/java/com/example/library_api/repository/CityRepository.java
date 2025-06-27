package com.example.library_api.repository;

import com.example.library_api.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CityRepository extends JpaRepository<City, Long> {}
