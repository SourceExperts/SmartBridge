package com.freemahn.smartbridge.repository;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.dao.match.Bridge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BridgeRepository extends JpaRepository<Bridge, Long>
{
    List<Bridge> findAllByCorporate(Corporate corporate);

    int countBridgeByMatchedStartup(Startup startup);
}
