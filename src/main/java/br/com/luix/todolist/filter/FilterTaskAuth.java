package br.com.luix.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.luix.todolist.users.IUserRepositoy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepositoy userRepositoy;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/task/")) {
            var authorization = request.getHeader("Authorization");
            var auhtEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(auhtEncoded);
            var authString = new String(authDecode);
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            var userExists = userRepositoy.findByUsername(username);
            System.out.println(userExists);
            if (userExists == null) {
                response.sendError(401, "Unauthoraized!");
            } else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), userExists.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("userId", userExists.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Unauthoraized!");
                }
            }
        }
        else {
            filterChain.doFilter(request, response);
        }

    }
}
