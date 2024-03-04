package com.example.test.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class TransactionUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "source_account_id",  referencedColumnName = "id")
    private BankAccount sourceAccount;
    @ManyToOne
    @JoinColumn(name = "destination_account_id",  referencedColumnName = "id")
    private BankAccount destinationAccount;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
