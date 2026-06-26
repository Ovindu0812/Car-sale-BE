package com.example.test.repository;

import com.example.test.entity.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {
    List<Property> findByType(String type);
    List<Property> findByAvailableTrue();
    Optional<Property> findByNameIgnoreCase(String name);
}
