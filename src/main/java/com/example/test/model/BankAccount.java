package com.example.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @OneToOne
    private User user;
    @Column(name = "balance", nullable = false)
    private Double balance;
    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL)
    private List<TransactionUser> outgoingTransactions;
    @OneToMany(mappedBy = "destinationAccount", cascade = CascadeType.ALL)
    private List<TransactionUser> incomingTransactions;
}
