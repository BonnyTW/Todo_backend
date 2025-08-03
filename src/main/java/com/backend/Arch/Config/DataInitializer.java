package com.backend.Arch.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.backend.Arch.Model.Users;
import com.backend.Arch.Reppo.UserReppo;



// Data initializer only for firstTime(when DB is empty)
@Component
public class DataInitializer implements CommandLineRunner{
	@Autowired
    private UserReppo userReppo;

	@Override
	public void run(String... args) throws Exception {
		if (userReppo.count() == 0) {
            Users admin = new Users();
            admin.setUsername("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("password"));
            admin.setRole("ADMIN");
            admin.setEmail("admin@gmail.com");
            userReppo.save(admin);
            System.out.println("✅ Default admin created: username=admin, password=password");
        }
	}
}
