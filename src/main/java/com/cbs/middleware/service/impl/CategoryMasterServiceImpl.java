package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CategoryMaster;
import com.cbs.middleware.repository.CategoryMasterRepository;
import com.cbs.middleware.service.CategoryMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategoryMaster}.
 */
@Service
@Transactional
public class CategoryMasterServiceImpl implements CategoryMasterService {

    private final Logger log = LoggerFactory.getLogger(CategoryMasterServiceImpl.class);

    private final CategoryMasterRepository categoryMasterRepository;

    public CategoryMasterServiceImpl(CategoryMasterRepository categoryMasterRepository) {
        this.categoryMasterRepository = categoryMasterRepository;
    }

    @Override
    public CategoryMaster save(CategoryMaster categoryMaster) {
        log.debug("Request to save CategoryMaster : {}", categoryMaster);
        return categoryMasterRepository.save(categoryMaster);
    }

    @Override
    public CategoryMaster update(CategoryMaster categoryMaster) {
        log.debug("Request to update CategoryMaster : {}", categoryMaster);
        return categoryMasterRepository.save(categoryMaster);
    }

    @Override
    public Optional<CategoryMaster> partialUpdate(CategoryMaster categoryMaster) {
        log.debug("Request to partially update CategoryMaster : {}", categoryMaster);

        return categoryMasterRepository
            .findById(categoryMaster.getId())
            .map(existingCategoryMaster -> {
                if (categoryMaster.getCastName() != null) {
                    existingCategoryMaster.setCastName(categoryMaster.getCastName());
                }
                if (categoryMaster.getCastCode() != null) {
                    existingCategoryMaster.setCastCode(categoryMaster.getCastCode());
                }
                if (categoryMaster.getCastCategoryCode() != null) {
                    existingCategoryMaster.setCastCategoryCode(categoryMaster.getCastCategoryCode());
                }

                return existingCategoryMaster;
            })
            .map(categoryMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryMaster> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryMasters");
        return categoryMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryMaster> findOne(Long id) {
        log.debug("Request to get CategoryMaster : {}", id);
        return categoryMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryMaster : {}", id);
        categoryMasterRepository.deleteById(id);
    }
}
