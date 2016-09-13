package com.kugou.limos.contracts.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Max on 2016/8/22.
 */
@Priority(12)
public class ExecutionFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExecutionFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("ExecutionFilter init...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("ExecutionFilter doFilter...");
    }

    @Override
    public void destroy() {
        LOGGER.warn("ExecutionFilter destroy...");
    }
}
