package org.aidan.shiro.resolver;

import org.aidan.shiro.permission.UrlPermission;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

/**
 * url权限解析器
 *
 * @author aidan
 */
public class UrlPermissionResolver implements PermissionResolver {

    @Override
    public Permission resolvePermission(String permissionString) {
//        if (permissionString.startsWith("/")) {
        return new UrlPermission(permissionString);
//    }
//        return new WildcardPermission(permissionString);
    }

}