package com.cbs.middleware.repository;

import com.cbs.middleware.domain.SeasonMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SeasonMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonMasterRepository extends JpaRepository<SeasonMaster, Long> {
    List<SeasonMaster> findBySeasonNameIsContaining(String seasonName);

    List<SeasonMaster> findAllBySeasonName(String seasonName);
}
