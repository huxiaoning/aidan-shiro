package org.aidan.shiro;

import org.aidan.shiro.authenticator.CustomizedModularRealmAuthenticator;
import org.aidan.shiro.cache.RedisCacheManager;
import org.aidan.shiro.filter.ResourceCheckFilter;
import org.aidan.shiro.realm.AdminRealm;
import org.aidan.shiro.realm.UserRealm;
import org.aidan.shiro.resolver.UrlPermissionResolver;
import org.aidan.shiro.session.RedisSessionDao;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * shiro 配置
 *
 * @author aidan
 */
@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());


        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("resourceCheckFilter", resourceCheckFilter());

        shiroFilterFactoryBean.setFilters(filters);

        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/**", "authc,resourceCheckFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setAuthenticator(customizedModularRealmAuthenticator());
        List<Realm> realmList = new ArrayList<>();
        realmList.add(userRealm());
        realmList.add(adminRealm());
        defaultWebSecurityManager.setRealms(realmList);

        defaultWebSecurityManager.setSessionManager(sessionManager());

        defaultWebSecurityManager.setCacheManager(redisCacheManager());

        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public ResourceCheckFilter resourceCheckFilter() {
        ResourceCheckFilter resourceCheckFilter = new ResourceCheckFilter();

        return resourceCheckFilter;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();

        return lifecycleBeanPostProcessor;
    }


    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        return hashedCredentialsMatcher;
    }

    @Bean
    public UrlPermissionResolver urlPermissionResolver() {
        UrlPermissionResolver urlPermissionResolver = new UrlPermissionResolver();

        return urlPermissionResolver;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public FilterRegistrationBean registration(ResourceCheckFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    /**
     * 会话管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDao());
        return sessionManager;
    }

    @Bean
    public RedisSessionDao redisSessionDao() {
        RedisSessionDao redisSessionDao = new RedisSessionDao();
        return redisSessionDao;
    }


    /**
     * 多realm配置
     */
    @Bean
    public CustomizedModularRealmAuthenticator customizedModularRealmAuthenticator() {
        CustomizedModularRealmAuthenticator customizedModularRealmAuthenticator = new CustomizedModularRealmAuthenticator();
        customizedModularRealmAuthenticator.setAuthenticationStrategy(atLeastOneSuccessfulStrategy());
        return customizedModularRealmAuthenticator;
    }

    @Bean
    public AtLeastOneSuccessfulStrategy atLeastOneSuccessfulStrategy() {
        AtLeastOneSuccessfulStrategy atLeastOneSuccessfulStrategy = new AtLeastOneSuccessfulStrategy();
        return atLeastOneSuccessfulStrategy;
    }

    @Bean
    public AdminRealm adminRealm() {
        AdminRealm adminRealm = new AdminRealm();
        adminRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return adminRealm;
    }


    /**
     * 缓存管理
     */

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        return redisCacheManager;
    }

    /**
     * 记住我功能
     */

    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie());
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("rememberMe");
        // -1 浏览器关闭之后失效,相当于没有记住我
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }
}
