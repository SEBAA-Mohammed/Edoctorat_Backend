package com.estf.edoctorat.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.estf.edoctorat.models.TokenConfirmation;
import com.estf.edoctorat.models.UserModel;


@Repository
public interface TokenConfirmationRepository extends JpaRepository<TokenConfirmation, Long> {
    Optional<TokenConfirmation> findByToken(String token);
    Optional<TokenConfirmation> findByUser(UserModel user);
    @Query("SELECT tc FROM TokenConfirmation tc WHERE tc.token = :token AND tc.expiryDate > CURRENT_TIMESTAMP")
    Optional<TokenConfirmation> findValidToken(String token);
}