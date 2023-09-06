package com.demo.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.util.Objects;

@Order(1)
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String endpoint = ((HttpServletRequest) request).getRequestURI();
        //chain.doFilter(request, response);
        //return;
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        
        HttpSession session = httpRequest.getSession(false); // Retrieve the session, or null if it doesn't exist
        if(endpoint.equals("/api/login") ) {
        	chain.doFilter(request, response);
        	System.out.print("------------------------------");
        	return;
        } 
         try {
        	 String token = extractToken(httpRequest);
             if (token != null) {
                 Claims claims = Jwts.parserBuilder()
                         .setSigningKey(Keys.hmacShaKeyFor("abcdefghijklmnoprqssfijsbdfeeeeeeeeeeeeeeeeeeeeeeef".getBytes()))
                         .build()
                         .parseClaimsJws(token)
                         .getBody();

                 String username = claims.getSubject();
                 if(username!=null) {
                	 chain.doFilter(request, response);
                 }
                 else {
                	 httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                	 httpResponse.getWriter().write("Token expired or invalid.");
                     return;
                 }
             }else {
            	 httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            	 httpResponse.getWriter().write("Token expired or invalid.");
                 return;
             }
             
                        
         }
         catch (ExpiredJwtException | MalformedJwtException e) {
             // Handle token exceptions (e.g., expired or invalid token)
        	 httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        	 httpResponse.getWriter().write("Token expired or invalid.");
             return;
         }
              
//      if (session != null) {
//      // if (session == null ) {
//    	   chain.doFilter(httpRequest, httpResponse); // Continue processing the request
//            return;
//        } else {
//        	 httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             httpResponse.getWriter().write("Unauthorized: Authentication required");
//             return;
//        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    	int a=1;
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
    
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (Objects.nonNull(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
}
