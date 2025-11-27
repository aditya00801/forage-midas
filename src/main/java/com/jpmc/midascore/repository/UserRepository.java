package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.UserRecord; // Import UserRecord
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserRecord, Long> {
    UserRecord findByName(String name);
    UserRecord findById(long id); // Ensure this matches what your code expects
}