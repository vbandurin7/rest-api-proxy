package com.vk.restapiproxy.database.repository;

import com.vk.restapiproxy.database.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    int countByName(Role.Name name);
    Role findByName(Role.Name name);
}
