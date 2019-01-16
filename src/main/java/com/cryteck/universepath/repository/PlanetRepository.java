package com.cryteck.universepath.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryteck.universepath.model.Planet;

public interface PlanetRepository extends JpaRepository<Planet,String> {

}
