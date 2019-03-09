package org.aidan;

import org.aidan.shiro.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 自定义realm单元测试
 */
public class CustomerRealmTest {

    private CustomerRealm realm = new CustomerRealm();

    @Before
    public void addUser() {

    }

    @Test
    public void testAuthentication() {
        // 1 构建SecurityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);
        // 2 主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken("aidan", "123456");
        // org.apache.shiro.authc.UnknownAccountException | org.apache.shiro.authc.IncorrectCredentialsException
        subject.login(token);

        // 3 校验是否认证通过
        boolean authenticated = subject.isAuthenticated();
        // true
        System.out.println("认证了吗?" + authenticated);


        // org.apache.shiro.authz.UnauthorizedException
        subject.checkRole("admin");
        subject.checkRoles("admin", "user");
        // org.apache.shiro.authz.UnauthorizedException
        subject.checkPermission("user:delete");
        subject.checkPermissions("user:delete", "user:select");

        // 登出
        subject.logout();

        authenticated = subject.isAuthenticated();
        // false
        System.out.println("认证了吗?" + authenticated);

    }
}
