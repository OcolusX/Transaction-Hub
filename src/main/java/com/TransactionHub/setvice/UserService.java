package com.TransactionHub.setvice;

import com.TransactionHub.model.User;
import com.TransactionHub.repository.EmailRepository;
import com.TransactionHub.repository.PhoneNumberRepository;
import com.TransactionHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    public User create(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        if(emailRepository.existsByEmail(user.getEmails().iterator().next().getEmail())) {
            throw new RuntimeException("Пользовтаель с ткаим адресом почты уже существует");
        }
        if(phoneNumberRepository.existsByNumber(user.getPhoneNumbers().iterator().next().getNumber())) {
            throw new RuntimeException("Пользователь с таким номером телефона уже существует");
        }

        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
