package com.ttn.project2.Service;

import com.ttn.project2.Model.ResetToken;
import com.ttn.project2.repository.ResetTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResetTokenService {

    @Autowired
    ResetTokenRepo resetTokenRepository;

    public void saveResetToken(ResetToken token) {
        resetTokenRepository.save(token);
    }

    public ResetToken getToken(String token) {
        return resetTokenRepository.findByToken(token);
    }
}
