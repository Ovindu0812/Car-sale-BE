package com.example.test.repository;

import com.example.test.entity.Electronic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ElectronicRepository extends MongoRepository<Electronic, String> {
    List<Electronic> findByType(String type);
    List<Electronic> findByAvailableTrue();
    Optional<Electronic> findByNameIgnoreCase(String name);
}
