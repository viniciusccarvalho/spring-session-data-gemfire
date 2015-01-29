package org.springframework.session.data.gemfire.config.annotation.web.http;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.gemfire.GemfireRepository;
import org.springframework.session.data.gemfire.GemfireSessionRepository;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;


@Configuration
public class GemfireHttpSessionConfiguration implements ImportAware,
		BeanClassLoaderAware {

	private HttpSessionStrategy httpSessionStrategy;

	@Bean
	public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> springSessionRepositoryFilter(
			SessionRepository<S> sessionRepository) {
		SessionRepositoryFilter<S> sessionRepositoryFilter = new SessionRepositoryFilter<S>(
				sessionRepository);
		if (httpSessionStrategy != null) {
			sessionRepositoryFilter.setHttpSessionStrategy(httpSessionStrategy);
		}
		return sessionRepositoryFilter;
	}

	
	@Bean
	@Autowired
	public GemfireSessionRepository createSessionRepository(GemfireRepository repo){
		GemfireSessionRepository sessionRepo = new GemfireSessionRepository(repo);
		return sessionRepo;
	}
	
	public void setBeanClassLoader(ClassLoader arg0) {

	}

	public void setImportMetadata(AnnotationMetadata arg0) {

	}

	@Autowired(required = false)
	public void setHttpSessionStrategy(HttpSessionStrategy httpSessionStrategy) {
		this.httpSessionStrategy = httpSessionStrategy;
	}

}
