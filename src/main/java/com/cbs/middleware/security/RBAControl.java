package com.cbs.middleware.security;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.domain.Permission;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.repository.PermissionRepository;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import tech.jhipster.config.JHipsterProperties;

@Configuration("authentication")
public class RBAControl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    HttpServletResponse response;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    IssPortalFileRepository issPortalFileRepository;

    @Autowired
    IssFileParserRepository issFileParserRepository;

    @Autowired
    JwtParser jwtParser;

    @Autowired
    JHipsterProperties jHipsterProperties;

    private static String SELF = "SELF";
    private static String OWN = "OWN";
    private static String YES = "YES";
    private static String NO = "NO";

    public boolean hasPermision(Long userId, Long issPortalId, Long issFileParserId, String object, String action) throws Exception {
        String schemeWiseBranchCodeFromId = "";
        String pacsNumberFromId = "";

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

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (authorities.size() == 1) {
            GrantedAuthority authority = authorities.stream().findFirst().get();

            if (authority.toString().equals(AuthoritiesConstants.ANONYMOUS)) {
                return false;
            } else if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
                return true;
            }

            String token = "" + auth.getCredentials();
            byte[] keyBytes;
            String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret();
            if (!ObjectUtils.isEmpty(secret)) {
                keyBytes = Decoders.BASE64.decode(secret);
            } else {
                secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
                keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            }
            Key key = Keys.hmacShaKeyFor(keyBytes);
            jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
            String token1 = token.replace("Bearer ", "");
            Jws<Claims> parseClaimsJws = jwtParser.parseClaimsJws(token1);
            JwsHeader header = parseClaimsJws.getHeader();

            String schemeWiseBranchCodeFromToken = "";
            if (header.get(Constants.KCC_ISS_BRANCH_CODE_KEY) != null) {
                schemeWiseBranchCodeFromToken = "" + header.get(Constants.KCC_ISS_BRANCH_CODE_KEY);
            }

            String pacsNumberFromToken = "";
            if (header.get(Constants.PACKS_CODE_KEY) != null) {
                pacsNumberFromToken = "" + header.get(Constants.PACKS_CODE_KEY);
            }

            Optional<IssPortalFile> issFilePortal = null;
            if (issPortalId != null) {
                issFilePortal = issPortalFileRepository.findById(issPortalId);
                if (issFilePortal.isPresent()) {
                    schemeWiseBranchCodeFromId = "" + issFilePortal.get().getSchemeWiseBranchCode();
                    pacsNumberFromId = "" + issFilePortal.get().getPacsCode();
                }
            }

            Optional<IssFileParser> issFileParser = null;
            if (issFileParserId != null) {
                issFileParser = issFileParserRepository.findById(issFileParserId);
                if (issFileParser.isPresent()) {
                    schemeWiseBranchCodeFromId = "" + issFileParser.get().getIssPortalFile().getSchemeWiseBranchCode();
                    schemeWiseBranchCodeFromId = "" + issFileParser.get().getSchemeWiseBranchCode();

                    pacsNumberFromId = "" + issFileParser.get().getPacsNumber();
                }
            }

            Permission permission = permissionRepository.findOneByObjectAndActionAndRole(object, action, authority.toString());

            if (permission == null) {
                return false;
            }
            if (permission.getPermission().equalsIgnoreCase(NO)) {
                return false;
            }


            if (permission.getScope().equalsIgnoreCase(OWN)) {
                if (
                    StringUtils.isBlank(schemeWiseBranchCodeFromId) &&
                    StringUtils.isBlank(pacsNumberFromId) &&
                    "VIEW".equalsIgnoreCase(action)
                ) {
                    return true;
                } else if (schemeWiseBranchCodeFromToken.toString().equalsIgnoreCase(schemeWiseBranchCodeFromId)) {
                    return true;
                } else {
                    return false;
                }
            }

            if (permission.getScope().equalsIgnoreCase(SELF)) {
                if (
                    StringUtils.isBlank(schemeWiseBranchCodeFromId) &&
                    StringUtils.isBlank(pacsNumberFromId) &&
                    "VIEW".equalsIgnoreCase(action)
                ) {
                    return true;
                }

                if (
                    schemeWiseBranchCodeFromToken.toString().equalsIgnoreCase(schemeWiseBranchCodeFromId) &&
                    pacsNumberFromToken.toString().equalsIgnoreCase(pacsNumberFromToken)
                ) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            throw new Exception("Provide single role to user");
        }
        return returnData;
    }
    

    public void authenticateByCode(String bankCode, String schemeWiseBranchCode, String packsNumber, String ENTITY_NAME) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        }
        Optional<User> user = userRepository.findOneByLogin(auth.getName());
        if (!user.isPresent()) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        }
        if (user.isPresent()) {

            if (StringUtils.isNotBlank(user.get().getPacsNumber()) && !user.get().getPacsNumber().equalsIgnoreCase(packsNumber)) {
                throw new ForbiddenAuthRequestAlertException("Unauthorized Operation", ENTITY_NAME, "unAuthorized");
            } else if (
                StringUtils.isNotBlank(user.get().getSchemeWiseBranchCode()) &&
                !user.get().getSchemeWiseBranchCode().equalsIgnoreCase(schemeWiseBranchCode)
            ) {
                throw new ForbiddenAuthRequestAlertException("Unauthorized Operation", ENTITY_NAME, "unAuthorized");
            } else if (StringUtils.isNotBlank(user.get().getBankCode()) && !user.get().getBankCode().equalsIgnoreCase(bankCode)) {
                throw new ForbiddenAuthRequestAlertException("Unauthorized Operation", ENTITY_NAME, "unAuthorized");
            }
        } else {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        }
    }

    /** RBAC function for on database object record permission */
    public boolean onDatabaseRecordPermission(String object, String action) throws Exception {
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
            } else if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
                return true;
            }
            Permission permission = permissionRepository.findOneByObjectAndActionAndRole(object, action, authority.toString());

            if (permission == null) {
                return false;
            }
            if (permission.getPermission().equalsIgnoreCase(YES)) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new Exception("Provide single role to user");
        }
    }

    /**
     *
     */
    public boolean userCheck(String login, String object, String action) throws Exception {
        String loginNameFromAPI = "";

        boolean returnData = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        if (!optUser.isPresent()) {
            return false;
        }
        if (StringUtils.isNotBlank(login)) {
            Optional<User> userFromId = userRepository.findOneByLogin(login);
            if (userFromId.isPresent()) {
                loginNameFromAPI = userFromId.get().getLogin();
            }
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (authorities.size() == 1) {
            GrantedAuthority authority = authorities.stream().findFirst().get();

            if (AuthoritiesConstants.ANONYMOUS.equals(authority.toString())) {
                return false;
            } else if (AuthoritiesConstants.ADMIN.equals(authority.toString())) {
                return true;
            } else if (AuthoritiesConstants.ROLE_BRANCH_ADMIN.equals(authority.toString())) {
                return true;
            } else if (AuthoritiesConstants.ROLE_BRANCH_USER.equals(authority.toString())) {
                if (loginNameFromAPI.equalsIgnoreCase(optUser.get().getLogin())) {
                    return true;
                }
            } else if (AuthoritiesConstants.ROLE_PACS_USER.equals(authority.toString())) {
                if (loginNameFromAPI.equalsIgnoreCase(optUser.get().getLogin())) {
                    return true;
                }
            }
        }
        return returnData;
    }

    /** RBAC function for super admin permission */
    public boolean superAdminPermission() throws Exception {
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
            } else if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new Exception("Provide single role to user");
        }
    }
    
    
    
    
    
    
	public boolean checkValidationForUsers(String code) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new UnAuthRequestAlertException("Access is denied", "", "unAuthorized");
		}
		Optional<User> user = userRepository.findOneByLogin(auth.getName());
		if (!user.isPresent()) {
			throw new UnAuthRequestAlertException("Access is denied", "", "unAuthorized");
		}
		if (user.isPresent()) {

			if (StringUtils.isNotBlank(user.get().getPacsNumber())&& user.get().getPacsNumber().equalsIgnoreCase(code)) {
				return true;
			} else if (StringUtils.isNotBlank(user.get().getSchemeWiseBranchCode()) && user.get().getSchemeWiseBranchCode().equalsIgnoreCase(code)) {
				return true;
			} else {
				throw new UnAuthRequestAlertException("Access is denied", "", "unAuthorized");
			}
		} else {
			throw new UnAuthRequestAlertException("Access is denied", "", "unAuthorized");
		}
	}
}
