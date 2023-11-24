package com.darkurfu.authservice.security;

import com.darkurfu.authservice.datamodels.enums.SessionStatus;
import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import com.darkurfu.authservice.service.SessionService;
import com.darkurfu.authservice.service.cryptutils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private final JWTUtil jwtUtil;
    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }


        jwt = authHeader.substring(7);
        if (jwt.isEmpty() || !jwtUtil.isTokenValid(jwt) || SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request,response);
            return;
        }

        Claims payload = jwtUtil.encryptJWT(jwt).getPayload();
        String sessionID = (String) payload.get("sessionId");
        try {
            SessionStatus sessionStatus = sessionService.getSessionStatus(sessionID);


            if (sessionStatus == SessionStatus.ACTIVE){
                String userId = (String) payload.get("userId");
                String role = (String) payload.get("role");

                UserDetails userDetails = userDetailsService.loadUserByUsername("");


                //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken();

                //SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


        } catch (NotFindTypeException e) {
            filterChain.doFilter(request, response);
            return;
        }


        filterChain.doFilter(request, response);
    }
}

