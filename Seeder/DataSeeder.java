package com.EChowk.EChowk.Seeder;

import com.EChowk.EChowk.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserService userService;
    @Override
    public void run(String... args) throws Exception {
        userService.seedAdminUser();
    }
}
