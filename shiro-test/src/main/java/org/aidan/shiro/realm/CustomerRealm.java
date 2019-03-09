package org.aidan.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义realm
 *
 * @author aidan
 */
public class CustomerRealm extends AuthorizingRealm {

    Map<String, String> userMap = new HashMap<String, String>(16) {{
        put("aidan", "123456");
    }};

    Map<String, List<String>> roleMap = new HashMap<String, List<String>>(16) {{
        put("aidan", Arrays.asList("admin", "user"));
    }};

    Map<String, List<String>> permissionMap = new HashMap<String, List<String>>(16) {{
        put("admin", Arrays.asList("user:select", "user:delete"));
        put("user", Arrays.asList("user:select"));
    }};

    public CustomerRealm() {
        super.setName("CustomerRealm");

    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> roleList = roleMap.get(userName);
        for (String role : roleList) {
            info.addRole(role);
            List<String> permissionList = permissionMap.get(role);
            for (String permission : permissionList) {
                info.addStringPermission(permission);
            }
        }


        return info;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1 从主体传过来的认证信息中，获得用户名
        String userName = (String) token.getPrincipal();

        // 2 通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(userName);
        if (password == null) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, password, super.getName());
        return info;
    }

    /**
     * 模拟查询数据库
     *
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }
}
