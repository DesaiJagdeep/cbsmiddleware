package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.ActivityType;
import com.cbs.middleware.repository.ActivityTypeRepository;
import com.cbs.middleware.service.ActivityTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ActivityType}.
 */
@Service
@Transactional
public class ActivityTypeServiceImpl implements ActivityTypeService {

    private final Logger log = LoggerFactory.getLogger(ActivityTypeServiceImpl.class);

    private final ActivityTypeRepository activityTypeRepository;

    public ActivityTypeServiceImpl(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    @Override
    public ActivityType save(ActivityType activityType) {
        log.debug("Request to save ActivityType : {}", activityType);
        return activityTypeRepository.save(activityType);
    }

    @Override
    public ActivityType update(ActivityType activityType) {
        log.debug("Request to update ActivityType : {}", activityType);
        return activityTypeRepository.save(activityType);
    }

    @Override
    public Optional<ActivityType> partialUpdate(ActivityType activityType) {
        log.debug("Request to partially update ActivityType : {}", activityType);

        return activityTypeRepository
            .findById(activityType.getId())
            .map(existingActivityType -> {
                if (activityType.getActivityType() != null) {
                    existingActivityType.setActivityType(activityType.getActivityType());
                }
                if (activityType.getActivityTypeCode() != null) {
                    existingActivityType.setActivityTypeCode(activityType.getActivityTypeCode());
                }

                return existingActivityType;
            })
            .map(activityTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityType> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityTypes");
        return activityTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityType> findOne(Long id) {
        log.debug("Request to get ActivityType : {}", id);
        return activityTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivityType : {}", id);
        activityTypeRepository.deleteById(id);
    }
}
