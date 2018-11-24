package com.freemahn.smartbridge.repository;

import com.freemahn.smartbridge.dao.Startup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long>
{
    List<Startup> findAllByMetadataParsed(boolean metadataParsed);

    List<Startup> findAllByMetadataCompanyIdNotNull();
}
