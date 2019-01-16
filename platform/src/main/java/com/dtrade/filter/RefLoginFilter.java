package com.dtrade.filter;

import com.dtrade.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//TODO remove in prod
@Slf4j
@Component
@Order(1)
public class RefLoginFilter  implements Filter {


    @Autowired
    private IAccountService accountService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(
    ServletRequest request,
    ServletResponse response, FilterChain chain) throws IOException, ServletException

    {
        HttpServletRequest req = (HttpServletRequest) request;
        String ref =  req.getParameter("l-ref");
        if(!StringUtils.isEmpty(ref)){
            accountService.loginByRef(ref);
        }

        chain.doFilter(request, response);
    }

}