package be.pxl.parking.config;

import be.pxl.parking.domain.*;
import be.pxl.parking.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class UserDataLoader implements CommandLineRunner {
    private static final Logger LOGGER = LogManager.getLogger(UserDataLoader.class);
    private final UserRepository userRepository;

    public UserDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            LOGGER.info("users already loaded...");
            return;
        }
        LOGGER.info("loading users...");
        try (BufferedReader reader = Files.newBufferedReader(Path.of("./src/main/resources/data/users.csv"))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(";");
                User user = new User(split[0], split[1], new ContactInfo(split[2], split[3]));
                user.setBankAccountDetails(new BankAccountDetails(Bank.valueOf(split[4]), split[5]));
                for (int i = 6; i < split.length; i++) {
                    user.addLicensePlate(split[i]);
                }
                userRepository.save(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}