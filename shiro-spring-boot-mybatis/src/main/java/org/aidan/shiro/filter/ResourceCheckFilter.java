package org.aidan.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 资源检查过滤器
 *
 * @author aidan
 */
public class ResourceCheckFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        String url = getPathWithinApplication(request);
        HttpServletRequest req = (HttpServletRequest) request;
        String method = req.getMethod();
        String requestURI = req.getRequestURI();
        StringBuffer requestURL = req.getRequestURL();
        boolean permitted = subject.isPermitted(url);
        return permitted;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        return false;
    }


}