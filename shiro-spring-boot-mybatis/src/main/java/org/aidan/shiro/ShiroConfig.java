package org.aidan.shiro;

import org.aidan.shiro.filter.ResourceCheckFilter;
import org.aidan.shiro.realm.CustomerRealm;
import org.aidan.shiro.resolver.UrlPermissionResolver;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);

        return hashedCredentialsMatcher;
    }

    @Bean
    public CustomerRealm customerRealm(@Autowired HashedCredentialsMatcher hashedCredentialsMatcher) {
        CustomerRealm customerRealm = new CustomerRealm();
        customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return customerRealm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/login", "anon");


        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }


    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Autowired CustomerRealm customerRealm, @Autowired ModularRealmAuthorizer modularRealmAuthorizer) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customerRealm);
        defaultWebSecurityManager.setAuthorizer(modularRealmAuthorizer);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired DefaultWebSecurityManager defaultWebSecurityManager, @Autowired ResourceCheckFilter resourceCheckFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("resourceCheckFilter", resourceCheckFilter);
        shiroFilterFactoryBean.setFilters(filtersMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/**", "authc,resourceCheckFilter");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);

        return creator;
    }

    /**
     * 过滤器：
     * anon	                org.apache.shiro.web.filter.authc.AnonymousFilter
     * authc	            org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * authcBasic	        org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
     * logout	            org.apache.shiro.web.filter.authc.LogoutFilter
     * noSessionCreation	org.apache.shiro.web.filter.session.NoSessionCreationFilter
     * perms	            org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
     * port	                org.apache.shiro.web.filter.authz.PortFilter
     * rest	                org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
     * roles	            org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
     * ssl  	            org.apache.shiro.web.filter.authz.SslFilter
     * user	                org.apache.shiro.web.filter.authc.UserFilter
     */

    @Bean
    public ResourceCheckFilter resourceCheckFilter() {
        return new ResourceCheckFilter();
    }

    @Bean
    public UrlPermissionResolver urlPermissionResolver() {
        return new UrlPermissionResolver();
    }

    @Bean
    public ModularRealmAuthorizer modularRealmAuthorizer(@Autowired CustomerRealm customerRealm, @Autowired UrlPermissionResolver urlPermissionResolver) {
        ModularRealmAuthorizer modularRealmAuthorizer = new ModularRealmAuthorizer();
        List<Realm> realms = new ArrayList<>();
        realms.add(customerRealm);
        modularRealmAuthorizer.setRealms(realms);
        modularRealmAuthorizer.setPermissionResolver(urlPermissionResolver);
        return modularRealmAuthorizer;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}
