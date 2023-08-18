package com.cbs.middleware.web.rest.utility;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssFileParser}.
 */
@Service
public class BankBranchPacksCodeGet {

    private static final String ENTITY_NAME = "BankBranchPacksCodeGet";

    @Autowired
    private UserRepository userRepository;

    /**
     * Function for get bank code, branch code and packs code from user token
     *
     * @return
     */
    public Map<String, String> getCodeNumber() {
        Map<String, String> branchOrPacksNumber = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        if (StringUtils.isNotBlank(optUser.get().getPacsNumber())) {
            branchOrPacksNumber.put(Constants.PACKS_CODE_KEY, optUser.get().getPacsNumber());
        } else if (StringUtils.isNotBlank(optUser.get().getBranchCode())) {
            branchOrPacksNumber.put(Constants.BRANCH_CODE_KEY, optUser.get().getBranchCode());
        } else if (StringUtils.isNotBlank(optUser.get().getBankCode())) {
            branchOrPacksNumber.put(Constants.BANK_CODE_KEY, optUser.get().getBankCode());
        } else {
            throw new UnAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }
        return branchOrPacksNumber;
    }
}
