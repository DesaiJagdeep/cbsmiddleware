package com.cbs.middleware.repository;

import com.cbs.middleware.domain.UserFileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CbsDataReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserFileDetailsRepository extends JpaRepository<UserFileDetails, Long>, JpaSpecificationExecutor<UserFileDetails> {}
