package com.vk.restapiproxy.database.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.restapiproxy.database.entity.Audit;
import com.vk.restapiproxy.database.entity.ResourceType;
import com.vk.restapiproxy.database.entity.User;
import com.vk.restapiproxy.database.repository.AuditRepository;
import com.vk.restapiproxy.database.repository.ResourceTypeRepository;
import jakarta.servlet.http.HttpServletRequest;

public class AuditService {

    private final AuditRepository auditRepository;

    private final ResourceTypeRepository resourceTypeRepository;

    public AuditService(AuditRepository auditRepository, ResourceTypeRepository resourceTypeRepository) {
        this.auditRepository = auditRepository;
        this.resourceTypeRepository = resourceTypeRepository;

        prepare();
    }

    private void prepare() {
        for (ResourceType.Name name : ResourceType.Name.values()) {
            if (resourceTypeRepository.countByName(name) == 0) {
                ResourceType resourceType = new ResourceType();
                resourceType.setName(name);
                resourceTypeRepository.save(resourceType);
            }
        }
    }

    public void save(Audit audit) {
        if (audit != null) {
            auditRepository.save(audit);
        }
    }

    public Audit create(HttpServletRequest request, boolean hasAccess) {
        Audit audit = new Audit();

        String requestURI = request.getRequestURI();
        if (!requestURI.startsWith("/api/")) {
            return null;
        }
        for (ResourceType.Name name : ResourceType.Name.values()) {
            if (requestURI.substring(5).startsWith(name.getName())) {
                audit.setResourceType(resourceTypeRepository.findResourceTypeByName(name));
            }
        }

        if (audit.getResourceType() == null) {
            return null;
        }

        User user = UserService.getUser();
        if (user != null) {
            audit.setUserId(user.getId());
        }

        try {
            audit.setRequestMethod(Audit.RequestMethod.valueOf(request.getMethod()));
        } catch (IllegalArgumentException e) {
            // don't want to save log for unsupported methods
            return null;
        }

        audit.setHasAccess(hasAccess);

        request.getParameterMap();
        try {
            audit.setRequestParams(new ObjectMapper().writeValueAsString(request.getParameterMap()));
        } catch (JsonProcessingException ignored) {
        }

        return audit;
    }
}
