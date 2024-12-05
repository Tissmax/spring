package fr.digi.hello.service;

import fr.digi.hello.entites.UserAccount;
import fr.digi.hello.repository.UserAccountRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserAccountService {


    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {
         save(new UserAccount("user", encode("password"), "ROLE_USER"),
                 new UserAccount("admin", encode("password"), "ROLE_ADMIN"));
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public void save(UserAccount... user) {
        userAccountRepository.saveAll(Arrays.asList(user));
    }

}
