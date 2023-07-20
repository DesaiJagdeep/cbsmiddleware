package com.cbs.middleware.security;

import com.cbs.middleware.domain.Permission;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.PermissionRepository;
import com.cbs.middleware.repository.UserRepository;
import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration("authentication")
public class RBAControl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    HttpServletResponse response;

    @Autowired
    PermissionRepository permissionRepository;

    private static String SELF = "SELF";
    private static String OWN = "OWN";
    private static String ALL = "ALL";
    private static String YES = "YES";
    private static String NO = "NO";
    private static String CREATE = "CREATE";
    private static String VIEW = "VIEW";
    private static String admin = "admin";
    private static String user = "user";

    public boolean hasPermision(Long userId, String object, String action) throws Exception {
        boolean returnData = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        if (!optUser.isPresent()) {
            return false;
        }
        String login = "";
        if (userId != null) {
            Optional<User> userFromId = userRepository.findById(userId);
            if (userFromId.isPresent()) {
                login = userFromId.get().getLogin();
            }
        }
        User userFromToken = optUser.get();

        String token = "" + auth.getCredentials();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (authorities.size() == 1) {
            GrantedAuthority authority = authorities.stream().findFirst().get();

            if (authority.toString().equals(AuthoritiesConstants.ANONYMOUS)) {
                return false;
            } else if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
                return true;
            }

            Permission permission = permissionRepository.findAllByObjectAndActionAndRole(object, action, authority.toString());
            if (permission == null) {
                return false;
            }
            if (permission.getPermission().equalsIgnoreCase(NO)) {
                return false;
            }
            if (permission.getScope().equalsIgnoreCase(OWN)) {
                return false;
            }
            if (permission.getScope().equalsIgnoreCase(SELF)) {
                selfCheck(auth, login);
                return false;
            }
        } else {
            throw new Exception("Provide single role to user");
        }
        return returnData;
    }

    /** RBAC function for self check */
    private boolean selfCheck(Authentication auth, String login) {
        if (!"".equalsIgnoreCase(login) && login.equalsIgnoreCase(auth.getName())) {
            return true;
        }
        return false;
    }

    /** RBAC function for common permission */
    public boolean commonPermissionsTo(String scope) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findOneByLogin(auth.getName());
        if (!user.isPresent()) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (authorities.size() == 1) {
            GrantedAuthority authority = authorities.stream().findFirst().get();
            if (authority.toString().equals(AuthoritiesConstants.ANONYMOUS)) {
                return false;
            } else if (
                scope.equalsIgnoreCase(ALL) &&
                authority.toString().equals(AuthoritiesConstants.ADMIN) ||
                authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_ADMIN)
            ) {
                return true;
            } else if (admin.equalsIgnoreCase(scope) && authority.toString().equals(AuthoritiesConstants.ADMIN)) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new Exception("Provide single role to user");
        }
    }
}
