package org.springframework.session.data.gemfire;

import org.springframework.data.repository.CrudRepository;

public interface GemfireRepository extends CrudRepository<GemfireSessionRepository.GemfireSession, String> {

}
