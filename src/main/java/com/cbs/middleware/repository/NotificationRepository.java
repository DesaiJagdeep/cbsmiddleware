package com.cbs.middleware.repository;

import com.cbs.middleware.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Notification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findTop6ByIsReadFalse(Pageable pageable);

    Page<Notification> findTop6ByIsReadFalseAndPacsNumber(Pageable pageable, String pacsNumber);

    Page<Notification> findTop6ByIsReadFalseAndSchemeWiseBranchCode(Pageable pageable, String schemeWiseBranchCode);

    Page<Notification> findAllByPacsNumber(Pageable pageable, String pacsNumber);

    Page<Notification> findAllBySchemeWiseBranchCode(Pageable pageable, String schemeWiseBranchCode);

    @Query("select count(*) from Notification notification where notification.isRead is false and notification.pacsNumber =:pacsNumber")
    Long findCountByIsReadFalseAndPacsNumber(@Param("pacsNumber") String pacsNumber);

    @Query(
        "select count(*) from Notification notification where notification.isRead is false and notification.schemeWiseBranchCode =:schemeWiseBranchCode"
    )
    Long findCountByIsReadFalseAndSchemeWiseBranchCode(@Param("schemeWiseBranchCode") String schemeWiseBranchCode);

    @Query("select count(*) from Notification notification where notification.isRead is false")
    Long findCountByIsReadFalse();
}
