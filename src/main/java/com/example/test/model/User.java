package com.example.test.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table( name = "person")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @ElementCollection
    @CollectionTable(name = "user_phone_numbers", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> phone;
    @ElementCollection
    @CollectionTable(name = "user_emails", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> email;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private BankAccount bankAccount;
}