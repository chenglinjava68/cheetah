package org.cheetah.commons.rs.filter;


import org.cheetah.commons.path.AntPathMatcher;
import org.cheetah.commons.path.PathMatcher;
import org.cheetah.commons.utils.Assert;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Max
 */
public abstract class ApiAccessFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private List<String> exclusivePaths = Collections.emptyList();
    private List<String> inclusivePaths = Collections.emptyList();

    @Override
    public final void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getAbsolutePath().getPath();
        for (String exclusivePath : exclusivePaths) {
            if (pathMatcher.matches(exclusivePath, path))
                return ;
        }

        if (inclusivePaths.isEmpty()) {
            preRequest(requestContext);
            return;
        }

        for (String pattern : inclusivePaths) {
            if (pathMatcher.matches(pattern, path))
                return ;
        }
    }

    /**
     * @param requestContext
     */
    protected abstract void preRequest(ContainerRequestContext requestContext) throws IOException;

    @Override
    public final void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        afterRequest(requestContext, responseContext);
    }

    /**
     * @param requestContext
     * @param responseContext
     * @throws IOException
     */
    protected void afterRequest(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // do nothing
    }

    /**
     * @return
     */
    protected final PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    /**
     * @param exclusivePaths
     */
    public final void setExclusivePaths(List<String> exclusivePaths) {
        Assert.notNull(exclusivePaths, "exclusivePaths must not be null.");
        this.exclusivePaths = exclusivePaths;
    }

    /**
     * @param inclusivePaths
     */
    public final void setInclusivePaths(List<String> inclusivePaths) {
        Assert.notNull(inclusivePaths, "inclusivePaths must not be null.");
        this.inclusivePaths = inclusivePaths;
    }
}
