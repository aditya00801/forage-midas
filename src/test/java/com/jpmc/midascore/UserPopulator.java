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
        // Create Waldorf (ID 1) and Statler (ID 2)
        UserRecord waldorf = new UserRecord("waldorf", 10000);
        userRepository.save(waldorf);

        UserRecord statler = new UserRecord("statler", 10000);
        userRepository.save(statler);

        // Create Wilbur (ID 3)
        UserRecord wilbur = new UserRecord("wilbur", 0);
        userRepository.save(wilbur);

        // Create extra users (IDs 4 to 20) to act as Senders
        // They need high balances so transactions don't fail validation
        for (int i = 4; i <= 20; i++) {
            UserRecord user = new UserRecord("user" + i, 50000);
            userRepository.save(user);
        }
    }
}