package com.freemahn.smartbridge.repository;

import com.freemahn.smartbridge.dao.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateRepository extends JpaRepository<Corporate, Long>
{
}
