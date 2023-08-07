package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.NotificationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Notification}.
 */
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        log.debug("Request to save Notification : {}", notification);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification update(Notification notification) {
        log.debug("Request to update Notification : {}", notification);
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> partialUpdate(Notification notification) {
        log.debug("Request to partially update Notification : {}", notification);

        return notificationRepository
            .findById(notification.getId())
            .map(existingNotification -> {
                if (notification.getTitle() != null) {
                    existingNotification.setTitle(notification.getTitle());
                }
                if (notification.getContent() != null) {
                    existingNotification.setContent(notification.getContent());
                }
                if (notification.getIsRead() != null) {
                    existingNotification.setIsRead(notification.getIsRead());
                }
                if (notification.getCreatedAt() != null) {
                    existingNotification.setCreatedAt(notification.getCreatedAt());
                }
                if (notification.getRecipient() != null) {
                    existingNotification.setRecipient(notification.getRecipient());
                }
                if (notification.getSender() != null) {
                    existingNotification.setSender(notification.getSender());
                }
                if (notification.getType() != null) {
                    existingNotification.setType(notification.getType());
                }

                return existingNotification;
            })
            .map(notificationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notification> findAll(Pageable pageable) {
        log.debug("Request to get all Notifications");
        return notificationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notification> findOne(Long id) {
        log.debug("Request to get Notification : {}", id);
        return notificationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Notification : {}", id);
        notificationRepository.deleteById(id);
    }
}
