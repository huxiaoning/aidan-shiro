package org.aidan.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.*;

/**
 * 自定义realm
 *
 * @author aidan
 */
public class CustomerRealm extends AuthorizingRealm {

    Map<String, String> userMap = new HashMap<String, String>(16) {{
        put("aidan", "123456");
    }};

    Map<String, Set<String>> roleMap = new HashMap<String, Set<String>>(16) {{
        put("aidan", new HashSet<>(Arrays.asList("admin", "user")));
    }};

    Map<String, Set<String>> permissionMap = new HashMap<String, Set<String>>(16) {{
        put("aidan", new HashSet<>(Arrays.asList("user:select", "user:delete")));
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

        /** 模拟从数据库中查询角色与权限和集合 */
        Set<String> roleSet = getRolesByUserName(userName);
        Set<String> permissionSet = getPermissionsByUserName(userName);

        info.addRoles(roleSet);
        info.addStringPermissions(permissionSet);


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

    /**
     * 模拟从数据库查询角色集合
     *
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        return roleMap.get(userName);
    }

    /**
     * 模拟从数据库查询权限集合
     *
     * @param userName
     * @return
     */
    private Set<String> getPermissionsByUserName(String userName) {
        return permissionMap.get(userName);
    }

}
