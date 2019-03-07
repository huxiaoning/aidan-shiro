package org.aidan;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试授权
 *
 * @author aidan
 */
public class AuthorizationTest {

    private SimpleAccountRealm realm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        realm.addAccount("aidan", "123456", "admin", "user");
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

        // 登出
        subject.logout();

        authenticated = subject.isAuthenticated();
        // false
        System.out.println("认证了吗?" + authenticated);

    }
}
