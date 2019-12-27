package org.vizadev.ddcapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, org.vizadev.ddcapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, org.vizadev.ddcapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, org.vizadev.ddcapp.domain.User.class.getName());
            createCache(cm, org.vizadev.ddcapp.domain.Authority.class.getName());
            createCache(cm, org.vizadev.ddcapp.domain.User.class.getName() + ".authorities");
            createCache(cm, org.vizadev.ddcapp.domain.PersistentToken.class.getName());
            createCache(cm, org.vizadev.ddcapp.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, org.vizadev.ddcapp.domain.Organization.class.getName());
            createCache(cm, org.vizadev.ddcapp.domain.Organization.class.getName() + ".locations");
            createCache(cm, org.vizadev.ddcapp.domain.Location.class.getName());
            createCache(cm, org.vizadev.ddcapp.domain.Employee.class.getName());
            createCache(cm, org.vizadev.ddcapp.domain.Employee.class.getName() + ".briefings");
            createCache(cm, org.vizadev.ddcapp.domain.Employee.class.getName() + ".managers");
            createCache(cm, org.vizadev.ddcapp.domain.Employee.class.getName() + ".departments");
            createCache(cm, org.vizadev.ddcapp.domain.Department.class.getName());
            createCache(cm, org.vizadev.ddcapp.domain.Department.class.getName() + ".employees");
            createCache(cm, org.vizadev.ddcapp.domain.Task.class.getName());
            createCache(cm, org.vizadev.ddcapp.domain.Task.class.getName() + ".briefings");
            createCache(cm, org.vizadev.ddcapp.domain.Briefing.class.getName());
            createCache(cm, org.vizadev.ddcapp.domain.Briefing.class.getName() + ".tasks");
            createCache(cm, org.vizadev.ddcapp.domain.BriefingHistory.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
