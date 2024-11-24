package com.nagarro.bankhub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statement {
    @Id
    private String id;
    private String accountId;
    private String datefield;
    private String amount;
}
