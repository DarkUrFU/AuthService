package com.darkurfu.authservice.security;

import com.darkurfu.authservice.datamodels.enums.SessionStatus;
import com.darkurfu.authservice.exceptions.NotFindSessionException;
import com.darkurfu.authservice.exceptions.NotFindTypeException;
import com.darkurfu.authservice.datamodels.user.UserAuthInfo;
import com.darkurfu.authservice.service.SessionService;
import com.darkurfu.authservice.service.authutils.GrantedAuthUtil;
import com.darkurfu.authservice.service.cryptutils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;


@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private final JWTUtil jwtUtil;
    @Autowired
    private final SessionService sessionService;


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
        if (jwt.isEmpty() || !Objects.requireNonNull(jwtUtil).isTokenValid(jwt) || SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request,response);
            return;
        }

        Claims payload = jwtUtil.encryptJWT(jwt).getPayload();
        String sessionID = (String) payload.get("sessionId");
        try {
            assert sessionService != null;
            SessionStatus sessionStatus = sessionService.getSessionStatus(sessionID);


            if (sessionStatus == SessionStatus.ACTIVE){

                UserAuthInfo userAuthInfo = new UserAuthInfo(
                        UUID.fromString(payload.get("sessionId", String.class)),
                        UUID.fromString(payload.get("userId", String.class)),
                        payload.get("role", Integer.class)
                );


                if (userAuthInfo.getRole() != 0){
                    userAuthInfo.setPermissionsOfMapStrInt(
                            payload.get("permissions", HashMap.class)
                    );
                }


                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                                userAuthInfo,
                        null,
                        GrantedAuthUtil.getAuthorities(
                                userAuthInfo.getPermissions()
                        )
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


        } catch (NotFindTypeException e) {
            filterChain.doFilter(request, response);
            return;
        } catch (NotFindSessionException e){
            filterChain.doFilter(request, response);
            return;
        }

        Logger.getLogger("AAAAAAAAAA").info(SecurityContextHolder.getContext().getAuthentication().toString());

        filterChain.doFilter(request, response);
    }
}

