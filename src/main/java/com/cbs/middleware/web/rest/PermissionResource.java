package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.Permission;
import com.cbs.middleware.repository.PermissionRepository;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing Permission.
 */
@RestController
@RequestMapping("/api")
public class PermissionResource {

    //    @Autowired
    //    CustomeObject customeObject;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermissionRepository permissionRepository;
    private static final String ENTITY_NAME = "permission";

    public PermissionResource(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * POST /permissions : Create a new permission.
     *
     * @param permission the permission to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         permission, or with status 400 (Bad Request) if the permission has
     *         already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/permissionCreate")
    //    @PreAuthorize("@authentication.commonPermissionsTo('superAdmin')")
    public ResponseEntity<List<Permission>> createPermissionCustom(@RequestBody List<Permission> permission) throws URISyntaxException {
        List<Permission> result = new ArrayList<>();
        for (Permission permission2 : permission) {
            if (permission2.getId() != null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            Permission result1 = permissionRepository.save(permission2);
            result.add(result1);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * PUT /permissions : Updates an existing permission.
     *
     * @param permission the permission to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         permission, or with status 400 (Bad Request) if the permission is not
     *         valid, or with status 500 (Internal Server Error) if the permission
     *         couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/permission")
    //    @PreAuthorize("@authentication.commonPermissionsTo('superAdmin')")
    public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission) throws URISyntaxException {
        if (permission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Permission result = permissionRepository.save(permission);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /permissions : get all the permissions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of permissions
     *         in body
     */
    @GetMapping("/permission")
    //    @PreAuthorize("@authentication.commonPermissionsTo('superAdmin')")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissionList = permissionRepository.findAll();
        if (permissionList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(permissionList);
    }

    /**
     * GET /permissions/:id : get the "id" permission.
     *
     * @param id the id of the permission to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the permission,
     *         or with status 404 (Not Found)
     */
    @GetMapping("/permission/{id}")
    //    @PreAuthorize("@authentication.commonPermissionsTo('superAdmin')")
    public ResponseEntity<Permission> getPermission(@PathVariable Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        if (!permission.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(permission.get());
    }

    /**
     * DELETE /permissions/:id : delete the "id" permission.
     *
     * @param id the id of the permission to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/permission/{id}")
    //    @PreAuthorize("@authentication.commonPermissionsTo('superAdmin')")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
