package com.cbs.middleware.web.rest.utility;

import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.service.MailService;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NotificationDataUtility {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    MailService mailService;

    public void notificationData(String title, String content, boolean isRead, Instant createdAt, String type) {
        String bankCode = "";
        String schemeWiseBranchCode = "";
        String pacsNumber = "";
        NotificationEmailDTO notificationEmailDTO = new NotificationEmailDTO();
        notificationEmailDTO.setContent(content);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new UnAuthRequestAlertException("Access is denied", "ENTITY_NAME", "unAuthorized");
        }
        Optional<User> isUser = userRepository.findOneByLogin(auth.getName());
        if (!isUser.isPresent()) {
            throw new UnAuthRequestAlertException("Access is denied", "ENTITY_NAME", "unAuthorized");
        }

        User user = isUser.get();

        if (StringUtils.isNotBlank(user.getPacsNumber())) {
            // find all emails of related pacs and related branch user and branch admin and
            // all admin

            if (StringUtils.isNotBlank(user.getEmail())) {
                notificationEmailDTO.setSender(user.getEmail());
            } else {
                notificationEmailDTO.setSender(user.getLogin());
            }

            // find all relative users

            Set<String> findAllEmailByPacsNumber = new HashSet();
            findAllEmailByPacsNumber.addAll(userRepository.findAllEmailByPacsNumber(user.getPacsNumber()));
            findAllEmailByPacsNumber.addAll(
                userRepository.findAllEmailBySchemeWiseBranchCodeAndPacsNumberEmpty(user.getSchemeWiseBranchCode(), "")
            );
            findAllEmailByPacsNumber.addAll(userRepository.findAllEmailByBankCodeAndSchemeWiseBranchCodeEmpty(user.getBankCode(), ""));

            notificationEmailDTO.setReceiver(findAllEmailByPacsNumber);

            pacsNumber = user.getPacsNumber();
            schemeWiseBranchCode = user.getSchemeWiseBranchCode();
            bankCode = user.getBankCode();
        } else if (StringUtils.isNotBlank(user.getSchemeWiseBranchCode())) {
            // find all emails of related branch branch admin and all admin

            if (StringUtils.isNotBlank(user.getEmail())) {
                notificationEmailDTO.setSender(user.getEmail());
            } else {
                notificationEmailDTO.setSender(user.getLogin());
            }

            // find all relative users

            Set<String> findAllEmailByPacsNumber = new HashSet();
            findAllEmailByPacsNumber.addAll(
                userRepository.findAllEmailBySchemeWiseBranchCodeAndPacsNumberEmpty(user.getSchemeWiseBranchCode(), "")
            );
            findAllEmailByPacsNumber.addAll(userRepository.findAllEmailByBankCodeAndSchemeWiseBranchCodeEmpty(user.getBankCode(), ""));

            notificationEmailDTO.setReceiver(findAllEmailByPacsNumber);

            schemeWiseBranchCode = user.getSchemeWiseBranchCode();
            bankCode = user.getBankCode();
        } else if (StringUtils.isNotBlank(user.getBankCode())) {
            // find all admin emails

            if (StringUtils.isNotBlank(user.getEmail())) {
                notificationEmailDTO.setSender(user.getEmail());
            } else {
                notificationEmailDTO.setSender(user.getLogin());
            }

            // find all relative users

            Set<String> findAllEmailByPacsNumber = new HashSet();
            findAllEmailByPacsNumber.addAll(userRepository.findAllEmailByBankCodeAndSchemeWiseBranchCodeEmpty(user.getBankCode(), ""));

            notificationEmailDTO.setReceiver(findAllEmailByPacsNumber);

            bankCode = user.getBankCode();
        }

        senNotification(notificationEmailDTO, title, content, isRead, createdAt, type, bankCode, schemeWiseBranchCode, pacsNumber);
    }

    public void senNotification(
        NotificationEmailDTO notificationEmailDTO,
        String title,
        String content,
        boolean isRead,
        Instant createdAt,
        String type,
        String bankCode,
        String schemeWiseBranchCode,
        String pacsNumber
    ) {
        Notification notification = new Notification(
            title,
            content,
            isRead,
            createdAt,
            notificationEmailDTO.getReceiver().toString(), // recipient
            notificationEmailDTO.getSender(), // sender
            type,
            bankCode,
            schemeWiseBranchCode,
            pacsNumber
        );
        notificationRepository.save(notification);

        try {
            mailService.sendNotificationEmail(notificationEmailDTO);
        } catch (Exception e) {}
    }
}
