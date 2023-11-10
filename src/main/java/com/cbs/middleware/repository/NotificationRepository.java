package com.cbs.middleware.repository;

import com.cbs.middleware.domain.Notification;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Notification entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByIsReadFalse();

    List<Notification> findAllByIsReadFalseAndPacsNumber(String pacsNumber);

    List<Notification> findAllByIsReadFalseAndSchemeWiseBranchCode(String schemeWiseBranchCode);

    List<Notification> findTop10ByIsReadFalseAndPacsNumber(String pacsNumber);

    List<Notification> findTop10ByIsReadFalseAndSchemeWiseBranchCode(String schemeWiseBranchCode);

    List<Notification> findTop10ByIsReadFalse();

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
