package com.freemahn.smartbridge.repository;

import com.freemahn.smartbridge.dao.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long>
{
}
