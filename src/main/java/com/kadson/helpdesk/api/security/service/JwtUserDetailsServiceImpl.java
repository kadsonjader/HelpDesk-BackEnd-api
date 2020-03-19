package com.kadson.helpdesk.api.security.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kadson.helpdesk.api.entity.Usuario;
import com.kadson.helpdesk.api.security.jwt.JwtUserFactory;
import com.kadson.helpdesk.api.service.UsuarioService;


@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    		Usuario usuario = usuarioService.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException(String.format("Usuario não encontrado com nome '%s'.", email));
        } else {
            return JwtUserFactory.create(usuario);
        }
    }
	
}
