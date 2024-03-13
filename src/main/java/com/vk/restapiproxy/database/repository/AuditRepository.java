package com.vk.restapiproxy.database.repository;

import com.vk.restapiproxy.database.entity.Audit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends CrudRepository<Audit, Long> {
}
