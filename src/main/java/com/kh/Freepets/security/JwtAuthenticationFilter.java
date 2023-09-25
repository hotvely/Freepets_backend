package com.kh.Freepets.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// @Component 등록을 해 줘야 스프링을 컴파일 혹은 런타임시 클래스를 자체적으로 등록해서 참조할 수 있음
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{   // OncePerRequestFilter를 상속받아 스프링부트 필터에 한번 등록 하기 위해서

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        // request 에서 토큰 가져오기..
        String token = parseBearerToken(request);
        if (token != null && !token.equalsIgnoreCase("null"))
        {
            String memberId = tokenProvider.validateAndGetUserId(token);
            AbstractAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(memberId,
                                                              null,
                                                              AuthorityUtils.createAuthorityList());

            // 인증관련 객체에 request에서 넘긴 데이터 담아서 security context 에 담아서 holder에 저장해 주면 됨
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);

        }
        filterChain.doFilter(request, response);

    }

    private String parseBearerToken(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }


}
