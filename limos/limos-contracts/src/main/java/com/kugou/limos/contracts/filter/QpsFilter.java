package com.kugou.limos.contracts.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Max on 2016/8/22.
 */
public class QpsFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(QpsFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("QpsFilter init...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("QpsFilter doFilter...");
    }

    @Override
    public void destroy() {
        LOGGER.warn("QpsFilter destroy...");
    }
}
