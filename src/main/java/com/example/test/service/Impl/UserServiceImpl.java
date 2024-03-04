package com.example.test.service.Impl;

import com.example.test.dto.SearchForUserDto;
import com.example.test.dto.UserDto;
import com.example.test.exception.ResourceNotFoundException;
import com.example.test.model.BankAccount;
import com.example.test.model.User;
import com.example.test.model.message.ProgramResponse;
import com.example.test.repository.UserRepository;
import com.example.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private String patternPhone = "(7|8)\\d{10}";
    private String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private String patternFullNameRegex = "^[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+$";
    private String patternPassword = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$";
    private String patternDateFormatOne = "dd.MM.yyyy";
    private String patternDateFormatTwo = "dd-MM-yyyy";
    private String patternDateFormatTree = "dd/MM/yyyy";
    private Double patternBalance = Double.MAX_VALUE;

    @Override
    public User createUser(UserDto userDto) {
        User newUser = createUserEntity(userDto);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(validateBalance(userDto));
        newUser.setBankAccount(bankAccount);
        log.debug(ProgramResponse.USER_CREATED.getDescription(), newUser);
        return userRepository.save(newUser);
    }

    private User createUserEntity(UserDto userDto) {
        User user = new  User();
        Set<String> phones = new HashSet<>();
        Set<String> emails = new HashSet<>();
        user.setUsername(validateUsername(userDto.getUsername()));
        user.setPassword(validatePassword(userDto.getPassword()));
        phones.add(validatePhone(userDto.getPhone()));
        emails.add(validateEmail(userDto.getEmail()));
        user.setPhone(phones);
        user.setEmail(emails);
        user.setFullName(validateFullName(userDto.getFullName()));
        String dateOfBirth = userDto.getDateOfBirth();
        user.setDateOfBirth(validateDateOfBirth(dateOfBirth));
        return user;
    }
    private String validateUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException(ProgramResponse.EMPTY_USERNAME.getDescription());
        }
        Optional<User> existingUser = userRepository.findUsersByUsername(username);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException(ProgramResponse.USERNAME_IS_ALREADY_REGISTERED.getDescription());
        }
        return username;
    }
    private String validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException(ProgramResponse.EMPTY_PASSWORD.getDescription());
        }
        if (!password.matches(patternPassword)) {
            throw new IllegalArgumentException(ProgramResponse.INVALID_PASSWORD.getDescription());
        }
        return password;
    }
    private String validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException(ProgramResponse.EMPTY_EMAIL.getDescription());
        }
        if (email.matches(emailPattern)) {
            Optional<User> existingUser = userRepository.findUserByEmail(email);
            if (existingUser.isPresent()) {
                throw new IllegalArgumentException(ProgramResponse.EMAIL_IS_ALREADY_REGISTERED.getDescription());
            }
            return email;
        } else {
            throw new IllegalArgumentException(ProgramResponse.INVALID_EMAIL.getDescription());
        }
    }

    private String validatePhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException(ProgramResponse.EMPTY_PHONE.getDescription());
        }
        if (phone.matches(patternPhone)) {
            Optional<User> existingUser = userRepository.findUserByPhone(phone);
            if (existingUser.isPresent()) {
                throw new IllegalArgumentException(ProgramResponse.PHONE_IS_ALREADY_REGISTERED.getDescription());
            }
            return phone;
        } else {
            throw new IllegalArgumentException(ProgramResponse.INVALID_PHONE.getDescription());
        }
    }

    private String validateFullName(String fullName) {
        if (fullName == null) {
            return null;
        }
        if (fullName.matches(patternFullNameRegex)) {
            return fullName;
        }
        log.error(ProgramResponse.INVALID_FULL_NAME.getDescription());
        return null;
    }
    private LocalDate validateDateOfBirth(String dateOfBirthStr) {
        if (dateOfBirthStr == null) {
            return null;
        }

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern(patternDateFormatOne);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(patternDateFormatTwo);
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern(patternDateFormatTree);

        try {

            return LocalDate.parse(dateOfBirthStr, formatter1);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(dateOfBirthStr, formatter2);
            } catch (DateTimeParseException e2) {
                try{
                return LocalDate.parse(dateOfBirthStr, formatter3);
                } catch (DateTimeParseException e3) {
                    throw new IllegalArgumentException(ProgramResponse.INVALID_DATE_OF_BIRTH.getDescription());
                }
            }
        }
    }

    private Double validateBalance(UserDto userDto) {
        Double balance = userDto.getBalance();
        if (balance == null || balance <= 0 || balance > patternBalance) {
            throw new IllegalArgumentException(MessageFormat.format(ProgramResponse.INVALID_BALANCE.getDescription(),
                    patternBalance));
        }
        return balance;
    }
    @Override
    public  User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ProgramResponse.USER_NOT_FOUND
                        .getDescription(),userId)));
    }

    @Override
    public User findByEmail(String email){
        if (email == null) {
            throw new IllegalArgumentException(ProgramResponse.EMPTY_EMAIL.getDescription());
        }
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException(ProgramResponse.INVALID_EMAIL.getDescription());
        }
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundException(ProgramResponse.EMAIL_NOT_FOUND.getDescription());
        }
    }

    @Override
    public User findByPhone(String phone){
        if (phone == null) {
            throw new IllegalArgumentException(ProgramResponse.EMPTY_PHONE.getDescription());
        }
        if (!phone.matches(patternPhone)) {
            throw new IllegalArgumentException(ProgramResponse.INVALID_PHONE.getDescription());
        }
        Optional<User> optionalUser = userRepository.findUserByPhone(phone);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundException(ProgramResponse.PHONE_NOT_FOUND.getDescription());
        }
    }
    @Override
    public List<User> findUsersByDateOfBirth(SearchForUserDto searchForUserDto) {
        LocalDate dateOfBirth = validateDateOfBirth(searchForUserDto.getDateOfBirth());
        int offset = (searchForUserDto.getPage() - 1) * searchForUserDto.getPageSize();
        Optional<User> optionalUser = userRepository.findUsersByDateOfBirth(dateOfBirth);
        if (optionalUser.isPresent()) {
            return optionalUser.stream()
                    .filter(user -> user.getDateOfBirth() != null && user.getDateOfBirth().isAfter(dateOfBirth))
                    .skip(offset)
                    .limit(searchForUserDto.getPageSize())
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException(ProgramResponse.DATE_OF_BIRTH_NOT_FOUND.getDescription());
        }
    }

    @Override
    public User findByFullName(String name){
        if (name == null) {
            throw new IllegalArgumentException(ProgramResponse.EMPTY_FULL_NAME.getDescription());
        }
        User user = userRepository.findByFullNameStartingWith(name);
        if (user == null) {
            throw new ResourceNotFoundException(ProgramResponse.FULL_NAME_NOT_FOUND.getDescription());
        }

        return user;
    }

    @Override
    public void updatePhoneContact(String registeredPhone, String newPhone) {
        User existingUser = findByPhone(registeredPhone);
        Set<String> phones = existingUser.getPhone();
        if (phones.contains(newPhone)) {
            throw new IllegalArgumentException(ProgramResponse.PHONE_ALREADY_EXISTS.getDescription());
        }
        String newPhoneValid = validatePhone(newPhone);
        phones.add(newPhoneValid);
        existingUser.setPhone(phones);
        userRepository.save(existingUser);
        log.debug(ProgramResponse.PHONE_UPDATED.getDescription(), existingUser);
    }
    @Override
    public void updateEmailContact(String registeredEmail, String newEmail) {
        User existingUser = findByEmail(registeredEmail);
        Set<String> emails = existingUser.getEmail();
        if (emails.contains(newEmail)) {
            throw new IllegalArgumentException(ProgramResponse.EMAIL_ALREADY_EXISTS.getDescription());
        }
        String newEmailValid = validateEmail(newEmail);
        emails.add(newEmailValid);
        existingUser.setEmail(emails);
        userRepository.save(existingUser);
        log.debug(ProgramResponse.EMAIL_UPDATED.getDescription(), existingUser);
    }
    @Override
    public void deletePhoneContact(String phone) {
        User existingUser = findByPhone(phone);
        Set<String> phones = existingUser.getPhone();
        if (phones.contains(phone)) {
            if (phones.size() > 1) {
                phones.remove(phone);
                existingUser.setPhone(phones);
                userRepository.save(existingUser);
                log.debug(ProgramResponse.PHONE_DELETED.getDescription(), existingUser);
            } else {
                throw new IllegalArgumentException(ProgramResponse.LAST_PHONE_CANNOT_BE_DELETED.getDescription());
            }
        } else {
            throw new IllegalArgumentException(ProgramResponse.PHONE_NOT_FOUND.getDescription());
        }
    }
    @Override
    public void deleteEmailContact(String email) {
        User existingUser = findByEmail(email);
        Set<String> emails = existingUser.getEmail();
        if (emails.contains(email)) {
            if (emails.size() > 1) {
                emails.remove(email);
                existingUser.setEmail(emails);
                userRepository.save(existingUser);
                log.debug(ProgramResponse.EMAIL_DELETED.getDescription(), existingUser);
            } else {
                throw new IllegalArgumentException(ProgramResponse.LAST_EMAIL_CANNOT_BE_DELETED.getDescription());
            }
        } else {
            throw new IllegalArgumentException(ProgramResponse.EMAIL_NOT_FOUND.getDescription());
        }
    }
}

