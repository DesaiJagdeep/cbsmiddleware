package com.cbs.middleware.repository;

import com.cbs.middleware.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String BRANCH_USERS_BY_LOGIN_CACHE = "usersByLogin";

    String BRANCH_USERS_BY_EMAIL_CACHE = "usersByEmail";
    Optional<User> findOneByActivationKey(String activationKey);
    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);
    Optional<User> findOneByResetKey(String resetKey);
    List<User> findAllByEmailIgnoreCase(String email);
    Optional<User> findOneByLogin(String login);
    Optional<User> findOneByEmailIgnoreCase(String email);
    Optional<User> findOneByMobileNumber(String mobileNumber);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = BRANCH_USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = BRANCH_USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    Page<User> findAllBySchemeWiseBranchCode(String schemeWiseBranchCode, Pageable pageable);

    @Query(value = "select user.email from User user where user.pacsNumber =:pacsNumber")
    Set<String> findAllEmailByPacsNumber(@Param("pacsNumber") String pacsNumber);

    @Query(
        value = "select user.email from User user where user.schemeWiseBranchCode =:schemeWiseBranchCode and user.pacsNumber=:pacsNumber"
    )
    Set<String> findAllEmailBySchemeWiseBranchCodeAndPacsNumberEmpty(
        @Param("schemeWiseBranchCode") String schemeWiseBranchCode,
        @Param("pacsNumber") String pacsNumber
    );

    @Query(value = "select user.email from User user where user.bankCode =:bankCode and user.schemeWiseBranchCode=:schemeWiseBranchCode")
    Set<String> findAllEmailByBankCodeAndSchemeWiseBranchCodeEmpty(
        @Param("bankCode") String bankCode,
        @Param("schemeWiseBranchCode") String schemeWiseBranchCode
    );

    Optional<User> findOneById(Long userId);
}
