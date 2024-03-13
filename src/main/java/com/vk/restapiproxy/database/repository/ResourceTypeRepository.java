package com.vk.restapiproxy.database.repository;

import com.vk.restapiproxy.database.entity.ResourceType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceTypeRepository extends CrudRepository<ResourceType, Long> {
    int countByName(ResourceType.Name name);

    ResourceType findResourceTypeByName(ResourceType.Name name);
}
