package br.com.nathansantos.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Modificadores
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * Principais tipos retornáveis em métodos
     * String (texto)
     * integer (números inteiros)
     * float (números decimais, abrangência menor que o double)
     * double (números decimais, abrangência maior que o float)
     * char (caractere único)
     * Date (data)
     * void (sem retorno)
     */

    /**
     * Body
     */
    @PostMapping("/")
    public void create(@RequestBody UserModel userModel){
        System.out.println(userModel.getName());
    }
}
