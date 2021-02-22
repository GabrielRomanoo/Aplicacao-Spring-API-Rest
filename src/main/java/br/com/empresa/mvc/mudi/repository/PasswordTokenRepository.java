package br.com.empresa.mvc.mudi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.empresa.mvc.mudi.model.PasswordResetToken;
import br.com.empresa.mvc.mudi.model.User;

@Repository

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long>{

	PasswordResetToken findByToken(String token);
	
	@Query(value = "SELECT user_id FROM password_reset_token WHERE token = :token",
			nativeQuery = true)
	String findUserByToken(String token);
}
