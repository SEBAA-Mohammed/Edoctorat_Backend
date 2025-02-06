package com.estf.edoctorat.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class TokenConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String token;
    
    @OneToOne
    private UserModel user;
    
    private Date expiryDate;
    
    private boolean confirmed;
    
    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }
}
