package com.security.spring.repository;

import com.security.spring.model.Cards;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {
	
	List<Cards> findByCustomerId(long customerId);

}
