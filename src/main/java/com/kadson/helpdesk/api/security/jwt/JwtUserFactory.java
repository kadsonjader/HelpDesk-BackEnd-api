package com.kadson.helpdesk.api.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.kadson.helpdesk.api.entity.Usuario;
import com.kadson.helpdesk.api.enums.PerfilEnum;

public class JwtUserFactory {
	
	private JwtUserFactory() {
	}
	
	public static JwtUser create(Usuario usuario) {
        return new JwtUser(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getSenha(),
                mapToGrantedAuthorities(usuario.getProfile())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum) {
    		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); 
    		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString())); 
    		return   authorities ;
    }
}
