/*package com.ttn.project2.config;
import com.ttn.project2.Model.User;
import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
    public class CommandLineAppStartupRunner implements CommandLineRunner {
        @Autowired
        UserRepository userRepository;

        User user;

        @Override
        public void run(String...args) throws Exception {

            User admin=new User();
            admin.setId(1);
            admin.setEmail("aartijha703@gmail.com");
            admin.setPassword("Abcd@1234");

            userRepository.save(admin);

        }
    }*/