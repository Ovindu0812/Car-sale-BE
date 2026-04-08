package com.example.test.repository;

import com.example.test.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    
    List<Vehicle> findByType(String type);
    
    List<Vehicle> findByFuelType(String fuelType);
    
    List<Vehicle> findByYearBetween(Integer startYear, Integer endYear);
    
    List<Vehicle> findByPriceBetween(Double minPrice, Double maxPrice);
    
    List<Vehicle> findByAvailableTrue();
    
    Optional<Vehicle> findByNameIgnoreCase(String name);
}
