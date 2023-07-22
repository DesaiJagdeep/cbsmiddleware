package com.cbs.middleware.security;

import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.domain.Permission;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.repository.PermissionRepository;
import com.cbs.middleware.repository.UserRepository;
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
    private static String ALL = "ALL";
    private static String YES = "YES";
    private static String NO = "NO";
    private static String CREATE = "CREATE";
    private static String VIEW = "VIEW";
    private static String admin = "admin";
    private static String user = "user";

    public boolean hasPermision(Long userId, Long issPortalId, Long issFileParserId, String object, String action) throws Exception {
        String branchCodeFromId = "";
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

            String branchCodeFromToken = "";
            if (header.get("branchName") != null) {
                branchCodeFromToken = "" + header.get("branchCode");
            }

            String pacsNumberFromToken = "";
            if (header.get("branchName") != null) {
                pacsNumberFromToken = "" + header.get("pacsNumber");
            }

            Optional<IssPortalFile> issFilePortal = null;
            if (issPortalId != null) {
                issFilePortal = issPortalFileRepository.findById(issPortalId);
                if (issFilePortal.isPresent()) {
                    branchCodeFromId = "" + issFilePortal.get().getBranchCode();
                    pacsNumberFromId = "" + issFilePortal.get().getBranchCode();
                }
            }

            Optional<IssFileParser> issFileParser = null;
            if (issFileParserId != null) {
                issFileParser = issFileParserRepository.findById(issFileParserId);
                if (issFileParser.isPresent()) {
                    branchCodeFromId = "" + issFileParser.get().getIssPortalFile().getBranchCode();
                    branchCodeFromId = "" + issFilePortal.get().getBranchCode();
                    pacsNumberFromId = "" + issFilePortal.get().getBranchCode();
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
                if (StringUtils.isBlank(branchCodeFromId) && StringUtils.isBlank(pacsNumberFromId) && "VIEW".equalsIgnoreCase(action)) {
                    return true;
                } else if (branchCodeFromToken.toString().equalsIgnoreCase(branchCodeFromId)) {
                    return true;
                } else {
                    return false;
                }
            }

            if (permission.getScope().equalsIgnoreCase(SELF)) {
                if (StringUtils.isBlank(branchCodeFromId) && StringUtils.isBlank(pacsNumberFromId) && "VIEW".equalsIgnoreCase(action)) {
                    return true;
                }

                if (
                    branchCodeFromToken.toString().equalsIgnoreCase(branchCodeFromId) &&
                    pacsNumberFromToken.toString().equalsIgnoreCase(pacsNumberFromId)
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

    public void authenticateByCode(String bankCode, String branchCode, String packsNumber, String ENTITY_NAME) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(">>>>>>>>>>>>>bankCode>>>>>>>>>>>>>>>>>>" + bankCode);
        System.out.println(">>>>>>>>>>>>>>>branchCode>>>>>>>>>>>>>>>>" + branchCode);
        System.out.println(">>>>>>>>>>>>>>>>>>>packsNumber>>>>>>>>>>>>" + packsNumber);
        if (auth == null) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        }
        Optional<User> user = userRepository.findOneByLogin(auth.getName());
        if (!user.isPresent()) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        }
        if (user.isPresent()) {
            if (StringUtils.isNotBlank(user.get().getPacsNumber()) && !user.get().getPacsNumber().equalsIgnoreCase(packsNumber)) {
                throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
            } else if (StringUtils.isNotBlank(user.get().getBranchCode()) && !user.get().getBranchCode().equalsIgnoreCase(branchCode)) {
                throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
            } else if (StringUtils.isNotBlank(user.get().getBankCode()) && !user.get().getBankCode().equalsIgnoreCase(bankCode)) {
                throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
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
}
