/*
package com.v1.opensquad.config;

import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.service.AutenticacaoService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyTokenFilter implements HandlerInterceptor {

    private final AutenticacaoRepository autenticacaoRepository;

    private final AutenticacaoService autenticacaoService;

    public MyTokenFilter(AutenticacaoRepository autenticacaoRepository, AutenticacaoService autenticacaoService) {
        this.autenticacaoRepository = autenticacaoRepository;
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        // Validar o token
        if (token != null && autenticacaoService.verificar(token) != null) {
            // Token válido, continua o processamento
            return true;
        } else {
            // Token inválido, retorna uma resposta de erro
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return false;
        }
    }
}*/
