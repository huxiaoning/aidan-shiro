package org.aidan;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 04 jdbc realm test
 *
 * @author aidan
 */
public class JdbcRealmTest {
    private JdbcRealm realm = new JdbcRealm();

    @Before
    public void addUser() {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://47.98.171.62:3306/shiro_test?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("P2ssw0rd");

        realm.setDataSource(dataSource);


        realm.setAuthenticationQuery("select password from tt_user where name = ?");

        realm.setUserRolesQuery("SELECT " +
                "a.name " +
                "FROM " +
                "tt_role a " +
                "LEFT JOIN tt_user_role b ON a.id = b.role_id " +
                "LEFT JOIN tt_user c ON b.user_id = c.id " +
                "WHERE " +
                "c. NAME = ?");
        realm.setPermissionsQuery("SELECT a.name from tt_permission a " +
                "LEFT JOIN tt_role_permission b ON a.id = b.permission_id " +
                "left join tt_role c ON b.role_id = c.id " +
                "WHERE c.name = ?");
        realm.setPermissionsLookupEnabled(true);
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
