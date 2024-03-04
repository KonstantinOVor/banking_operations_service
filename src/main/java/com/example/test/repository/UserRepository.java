package com.example.test.repository;

import com.example.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.email e WHERE e = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.phone p WHERE p = ?1")
    Optional<User> findUserByPhone(String phone);

    Optional<User> findUsersByUsername(String username);

    User findByFullNameStartingWith(String fullName);

    @Query("SELECT u FROM User u WHERE u.dateOfBirth > :dateOfBirth ORDER BY u.dateOfBirth ASC")
    Optional<User> findUsersByDateOfBirth(@Param("dateOfBirth") LocalDate dateOfBirth);

}
