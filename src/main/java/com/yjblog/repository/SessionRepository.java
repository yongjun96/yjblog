package com.yjblog.repository;

import com.yjblog.domain.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
}
