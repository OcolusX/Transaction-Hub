package com.TransactionHub.service;

import com.TransactionHub.exception.EmailException;
import com.TransactionHub.exception.PhoneNumberException;
import com.TransactionHub.model.Email;
import com.TransactionHub.model.PhoneNumber;
import com.TransactionHub.model.User;
import com.TransactionHub.repository.EmailRepository;
import com.TransactionHub.repository.PhoneNumberRepository;
import com.TransactionHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameNotFoundException("Пользователь с таким именем уже существует");
        }
        if (emailRepository.existsByEmail(user.getEmails().iterator().next().getEmail())) {
            throw new EmailException("Пользовтаель с ткаим адресом почты уже существует");
        }
        if (phoneNumberRepository.existsByNumber(user.getPhoneNumbers().iterator().next().getNumber())) {
            throw new PhoneNumberException("Пользователь с таким номером телефона уже существует");
        }

        log.info("Новый пользователь: " + user);
        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User addPhoneNumber(User user, String phoneNumber) {
        if (phoneNumberRepository.existsByNumber(phoneNumber)) {
            throw new PhoneNumberException("Пользователь с таким номером телефона уже существует");
        }
        user.getPhoneNumbers().add(new PhoneNumber(phoneNumber));
        return userRepository.save(user);
    }

    public User removePhoneNumber(User user, String phoneNumber) {
        if (user.getPhoneNumbers().size() == 1) {
            throw new PhoneNumberException("Невозможно удалить последний номер телефона");
        }
        PhoneNumber byNumber = phoneNumberRepository.findByNumber(phoneNumber)
                .orElseThrow(() -> new PhoneNumberException("Такого номера телефона не существует"));
        user.getPhoneNumbers().remove(byNumber);
        phoneNumberRepository.delete(byNumber);
        return userRepository.save(user);
    }

    public User addEmail(User user, String email) {
        if (emailRepository.existsByEmail(email)) {
            throw new EmailException("Пользователь с таким адресом электронной почты уже существует");
        }
        user.getEmails().add(new Email(email));
        return userRepository.save(user);
    }

    public User removeEmail(User user, String email) {
        if (user.getEmails().size() == 1) {
            throw new EmailException("Невозможно удалить последний адрес электронной почты");
        }
        Email byEmail = emailRepository.findByEmail(email)
                .orElseThrow(() -> new EmailException("Такого адреса электронной почты не существует"));
        user.getEmails().remove(byEmail);
        emailRepository.delete(byEmail);
        return userRepository.save(user);
    }

}
