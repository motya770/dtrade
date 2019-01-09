package com.dtrade.repository.landquotes;

import com.dtrade.model.landquotes.LandingQuotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandingQuotesRepository extends JpaRepository<LandingQuotes, Long> {
    LandingQuotes findByName(String name);
}
