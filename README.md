# spring-session-data-gemfire
Skeleton for a gemfire support on spring-session.

If you want to try it, create a simple spring-boot app and add the following:

```java

@ComponentScan
@EnableAutoConfiguration
@EnableGemfireHttpSession
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
	}
	
	@Bean
    CacheFactoryBean cacheFactoryBean() {
        return new CacheFactoryBean();
    }

    @Bean
    LocalRegionFactoryBean<String, GemfireSessionRepository.GemfireSession> localRegionFactory(final GemFireCache cache) {
        return new LocalRegionFactoryBean<String, GemfireSessionRepository.GemfireSession>() {

            {
                setCache(cache);
                setName("spring.session.sessions");
                setClose(false);
            }
        };
    }
}

```
