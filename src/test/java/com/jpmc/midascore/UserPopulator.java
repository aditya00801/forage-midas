package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPopulator {

    @Autowired
    private UserRepository userRepository;

    public void populate() {
        // Create Waldorf
        UserRecord waldorf = new UserRecord("waldorf", 10000);
        userRepository.save(waldorf);

        // Create Statler
        UserRecord statler = new UserRecord("statler", 10000);
        userRepository.save(statler);
    }
}