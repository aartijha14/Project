package com.ttn.project2.repository;

import com.ttn.project2.Model.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetTokenRepo extends JpaRepository<ResetToken,Long> {
    public ResetToken findByToken(String resetToken);
    public void deleteById(Long id);
}
