package com.hasebul.quickChat.config;

import com.hasebul.quickChat.model.User;
import com.hasebul.quickChat.repository.AuthRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Seeds 3 default users into PostgreSQL on first run (idempotent).
 * Login credentials are embedded in the app for demo convenience.
 */
@Component
public class DataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Autowired
    private AuthRepo authRepo;

    /**
     * Seed users. Each entry: email, username, password, title, location, bio,
     * skills
     */
    private static final List<Object[]> SEED_USERS = List.of(
            new Object[] { "alice@quickchat.app", "Alice Chen", "password123",
                    "Senior Software Engineer", "San Francisco, CA",
                    "Passionate about scalable systems and open-source. I build things that matter.",
                    "TypeScript, React, Node.js, AWS, GraphQL",
                    "https://alicechen.dev", "Hiking, Photography, Open Source", "@alicechen_dev" },
            new Object[] { "bob@quickchat.app", "Bob Martinez", "password123",
                    "Full Stack Developer", "Austin, TX",
                    "Building the future of web with modern JS frameworks. Speaker & blogger.",
                    "Angular, Spring Boot, Docker, PostgreSQL, Kubernetes",
                    "https://bobmartinez.io", "Gaming, Cooking, Tech Blogging", "@bob_codes" },
            new Object[] { "carol@quickchat.app", "Carol Nguyen", "password123",
                    "ML Engineer", "Seattle, WA",
                    "Turning data into insights. ML/AI practitioner obsessed with model efficiency.",
                    "Python, TensorFlow, PyTorch, Spark, MLflow",
                    "https://carolnguyen.ml", "Yoga, Competitive Chess, Reading", "@carol_ml" });

    @Override
    public void run(ApplicationArguments args) {
        int seeded = 0;
        for (Object[] data : SEED_USERS) {
            String email = (String) data[0];
            if (authRepo.findByUserEmail(email).isEmpty()) {
                User user = new User((String) data[1], (String) data[2], email);
                user.setJoinDate(LocalDate.of(2024, 1, 15));
                user.setProfessionalTitle((String) data[3]);
                user.setLocation((String) data[4]);
                user.setBio((String) data[5]);
                user.setSkills((String) data[6]);
                user.setPortfolio((String) data[7]);
                user.setHobbies((String) data[8]);
                user.setInstagram((String) data[9]);
                user.setPublishedPostCount(0L);
                authRepo.save(user);
                seeded++;
                log.info("Seeded user: {}", email);
            }
        }
        if (seeded > 0) {
            log.info("DataSeeder: seeded {} user(s).", seeded);
        } else {
            log.info("DataSeeder: all seed users already exist, skipping.");
        }
    }
}
