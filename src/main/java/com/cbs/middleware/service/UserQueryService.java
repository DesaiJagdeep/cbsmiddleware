package com.cbs.middleware.service;

import java.util.List;

import com.cbs.middleware.service.dto.AdminUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbs.middleware.domain.User;
// for static metamodels
import com.cbs.middleware.domain.User_;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.service.criteria.UserCriteria;

import tech.jhipster.service.QueryService;



/**
 * Service for executing complex queries for {@link User} entities in the database.
 * The main input is a {@link UserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link User} or a {@link Page} of {@link User} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {

    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);

    private final UserRepository userRepository;

    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Return a {@link List} of {@link User} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<User> findByCriteriaOr(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecificationOr(criteria);
        return userRepository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public List<User> findByCriteriaAndBranchName(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecificationAndBranchName(criteria);
        return userRepository.findAll(specification);
    }


    @Transactional(readOnly = true)
    public List<User> findByCriteria(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link User} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<User> findByCriteria(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification, page);
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> findByCriteriaAndBranchName(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}", criteria, page);
        final Specification<User> specification = createSpecificationAndBranchName(criteria);
        return userRepository.findAll(specification, page).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> findByCriteriaOr(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecificationOr(criteria);
        return userRepository.findAll(specification, page).map(AdminUserDTO::new);
    }

//    @Transactional(readOnly = true)
//    public Page<User> findByCriteriaSchemeWiseBranchCode(
//        UserCriteria criteria,
//        Pageable page,
//        String schemeWiseBranchCode
//    ) {
//        log.debug("find by criteria : {}, page: {}", criteria, page);
//        // final Specification<User> specification = createSpecification(criteria);
//        // return userRepository.findAllBySchemeWiseBranchCode(schemeWiseBranchCode,specification, page);
//
//        StringFilter sf = new StringFilter();
//        sf.equals(schemeWiseBranchCode);
//        criteria.setSchemeWiseBranchCode(sf);
//
//        final Specification<User> specification = createSpecification(criteria);
//        return userRepository.findAll(specification, page);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<User> findByCriteriaPackNumber(UserCriteria criteria, Pageable page, String pacsNumber) {
//        log.debug("find by criteria : {}, page: {}", criteria, page);
//        //final Specification<User> specification = createSpecification(criteria);
//        //return userRepository.findAllByPacsNumber(pacsNumber,specification, page);
//
//        StringFilter sf = new StringFilter();
//        sf.equals(pacsNumber);
//        criteria.setPacsNumber(sf);
//
//        final Specification<User> specification = createSpecification(criteria);
//        return userRepository.findAll(specification, page);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<User> findByIssPortalCriteria(IssPortalFile issPortalFile, UserCriteria criteria, Pageable page) {
//        log.debug("find by criteria : {}, page: {}", criteria, page);
//        final Specification<User> specification = createSpecification(criteria);
//        return userRepository.findAllByIssPortalFile(issPortalFile, specification, page);
//    }
//
//    /**
//     * Return the number of matching entities in the database.
//     * @param criteria The object which holds all the filters, which the entities should match.
//     * @return the number of matching entities.
//     */
//    @Transactional(readOnly = true)
//    public long countByCriteria(UserCriteria criteria) {
//        log.debug("count by criteria : {}", criteria);
//        final Specification<User> specification = createSpecification(criteria);
//        return userRepository.count(specification);
//    }

    /**
     * Function to convert {@link UserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), User_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), User_.login));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), User_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), User_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), User_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), User_.email));
            }
            if (criteria.getMobileNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMobileNumber(), User_.mobileNumber));
            }
            if (criteria.getPacsName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsName(), User_.pacsName));
            }

            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), User_.branchName));
            }

        }
        return specification;
    }





    protected Specification<User> createSpecificationOr(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.or(buildRangeSpecification(criteria.getId(), User_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.or(buildStringSpecification(criteria.getLogin(), User_.login));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getFirstName(), User_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getMiddleName(), User_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getLastName(), User_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.or(buildStringSpecification(criteria.getEmail(), User_.email));
            }
            if (criteria.getMobileNumber() != null) {
                specification =
                    specification.or(buildStringSpecification(criteria.getMobileNumber(), User_.mobileNumber));
            }
            if (criteria.getPacsName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getPacsName(), User_.pacsName));
            }

            if (criteria.getBranchName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getBranchName(), User_.branchName));
            }

        }
        return specification;
    }



    protected Specification<User> createSpecificationAndBranchName(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
//            if (criteria.getDistinct() != null) {
//                specification = specification.and(distinct(criteria.getDistinct()));
//            }
            if (criteria.getId() != null) {
                specification = specification.or(buildRangeSpecification(criteria.getId(), User_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.or(buildStringSpecification(criteria.getLogin(), User_.login));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getFirstName(), User_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getMiddleName(), User_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getLastName(), User_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.or(buildStringSpecification(criteria.getEmail(), User_.email));
            }
            if (criteria.getMobileNumber() != null) {
                specification =
                    specification.or(buildStringSpecification(criteria.getMobileNumber(), User_.mobileNumber));
            }
            if (criteria.getPacsName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getPacsName(), User_.pacsName));
            }

            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), User_.branchName));
            }

        }
        return specification;
    }
}
