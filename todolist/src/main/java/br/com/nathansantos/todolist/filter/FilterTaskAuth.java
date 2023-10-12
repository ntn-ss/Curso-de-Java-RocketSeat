package br.com.nathansantos.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.nathansantos.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
                var servletPath = req.getServletPath();
                if (servletPath.startsWith("/tasks/")) {
                    /***
                    * pegar a autenticação (usuário e senha)
                    * interpretar usuário e senha
                    * procurar usuário
                    * validar senha
                    * seguir em frente
                    */
                    
                // pegar a autenticação (usuário e senha)
                var authorization = req.getHeader("Authorization");
                    
                // interpretar usuário e senha
                
                // remove a palavra "Basic" e o espaço em branco em frente a ela
                var authEncoded = authorization.substring("Basic".length()).trim();
                byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
                var authString = new String(authDecoded);

                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];
                
                // procurar usuário
                var user = this.userRepository.findByUsername(username);
                
                if (user==null) {
                    res.sendError(401, "Usuário não encontrado.");
                }
                
                else {
                    // validar senha
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if (!passwordVerify.verified) {
                        res.sendError(401, "Senha incorreta.");
                    }
                    else {
                        // seguir em frente
                        req.setAttribute("idUser", user.getId());
                        filterChain.doFilter(req, res);
                    }
                }
                }
                else {
                        // seguir em frente
                        filterChain.doFilter(req, res);
                    }
                
    }
    
}