package com.auth1.auth.learning.service;

import com.auth1.auth.learning.dtos.SendEmailMessageDto;
import com.auth1.auth.learning.model.Token;
import com.auth1.auth.learning.model.User;
import com.auth1.auth.learning.repository.TokenRepository;
import com.auth1.auth.learning.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private KafkaTemplate<String ,String > kafkaTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public User signUp(String email , String password , String name){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);
        SendEmailMessageDto message = new SendEmailMessageDto();
        message.setFrom("support@scaler.com");
        message.setTo(email);
        message.setSubject("Welcome to Service");
        message.setBody("Hello! Looking forward to have you on our platform");

        try {
            kafkaTemplate.send(
                    "sendEmail",
                    objectMapper.writeValueAsString(message)
            );
        }
        catch (Exception e) {
        }
        return savedUser;
    }

    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new RuntimeException("Invalid user or password");
        }
        User user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(password , user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        Token token = new Token();
        token.setUser(user);
        token.setValue(UUID.randomUUID().toString());

        Date expiredDate = getExpiredDate();
        token.setExpireAt(expiredDate);

        return tokenRepository.save(token);
    }

    private Date getExpiredDate() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(new Date());
        calendarDate.add(Calendar.DAY_OF_MONTH , 30);
        Date expiredDate = calendarDate.getTime();
        return expiredDate;
    }

    public void logout(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEquals(
          token , false);

        if(tokenOptional.isEmpty()){
            throw new RuntimeException("Token is Invalid");
        }
        Token tokenObject = tokenOptional.get();
        tokenObject.setDeleted(true);

        tokenRepository.save(tokenObject);
    }

    public boolean validateToken(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEqualsAndExpireAtGreaterThan(
                token , false , new Date());

        if(tokenOptional.isEmpty()){
            return false;
        }
        return true;
    }
}

