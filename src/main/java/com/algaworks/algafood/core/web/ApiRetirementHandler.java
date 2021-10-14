package com.algaworks.algafood.core.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiRetirementHandler extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * Status significa um erro do cliente
         * que essa api não existe, foi desligada
         */
        if(request.getRequestURI().startsWith("/v2")) {
            response.setStatus(HttpStatus.GONE.value());
            return false;
        }

       /* if(request.getRequestURI().startsWith("/v1")) {
            response.setHeader("X-AlgaFood-Deprecation", "Esta versão da API está depreciada e deixará de existr a partir de 25/12/2021 Use a versão mais atual da API.");;
        }
       */

        return true;
    }
}
