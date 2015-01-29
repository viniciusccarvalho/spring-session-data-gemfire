package org.springframework.session.data.gemfire.config.annotation.web.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.session.data.gemfire.GemfireSessionRepository;

import com.gemstone.gemfire.cache.GemFireCache;

@Configuration
public class GemfireLocalConfig {

	@Bean
    CacheFactoryBean cacheFactoryBean() {
        return new CacheFactoryBean();
    }

    @Bean
    LocalRegionFactoryBean<String, GemfireSessionRepository.GemfireSession> localRegionFactory(final GemFireCache cache) {
        return new LocalRegionFactoryBean<String, GemfireSessionRepository.GemfireSession>() {

            {
                setCache(cache);
                setName("session");
                setClose(false);
            }
        };
    }
	
}
