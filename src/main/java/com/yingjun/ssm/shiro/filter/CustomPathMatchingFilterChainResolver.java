package com.yingjun.ssm.shiro.filter;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-25
 * <p>Version: 1.0
 */
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {

    private CustomDefaultFilterChainManager customDefaultFilterChainManager;

    public void setCustomDefaultFilterChainManager(CustomDefaultFilterChainManager customDefaultFilterChainManager) {
        this.customDefaultFilterChainManager = customDefaultFilterChainManager;
        setFilterChainManager(customDefaultFilterChainManager);
    }

    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }
        String requestURI = getPathWithinApplication(request);
        System.out.println("-requestURI-->" + requestURI);
        List<String> chainNames = new ArrayList<String>();
        //the 'chain names' in this implementation are actually path patterns defined by the user.  We just use them
        //as the chain name for the FilterChainManager's requirements
        System.out.println("-filterChainManager.getChainNames()-->" + filterChainManager.getChainNames());
        for (String pathPattern : filterChainManager.getChainNames()) {
            // If the path does match, then pass on to the subclass implementation for specific checks:
            if (pathMatches(pathPattern, requestURI)) {
                chainNames.add(pathPattern); // 这里就是与源码不同的地方，源码此处立马返回（返回第一个匹配的filter），修改后就是返回所有匹配的filter
            }
        }
        if(chainNames.size() == 0) {
            return null;
        }
        System.out.println("-匹配到的chainNames-->" + chainNames);
        // 如果匹配到的拦截器链包含anon的，例如/static/**
        HashSet<String> anonChainNames = Sets.newHashSet("/static/**", "/register/**", "/api/**");
        Set<String> chainNameSet = new HashSet<String>();
        chainNameSet.addAll(chainNames);
        Sets.SetView<String> intersection = Sets.intersection(chainNameSet, anonChainNames);
        if(intersection!=null && intersection.size()>0){
            chainNames.clear();
            chainNames.addAll(intersection);
            System.out.println("-由于anon，修改后的chainNames-->" + chainNames);
        }
        return customDefaultFilterChainManager.proxy(originalChain, chainNames);
    }
}
