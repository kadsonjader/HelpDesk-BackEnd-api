package com.kadson.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kadson.helpdesk.api.entity.Usuario;
import com.kadson.helpdesk.api.enums.PerfilEnum;
import com.kadson.helpdesk.api.repository.UsuarioRepository;


@SpringBootApplication
public class HelpDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpDeskApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            initUsers(usuarioRepository, passwordEncoder);
        };

    }
    
	private void initUsers(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        Usuario admin = new Usuario();
        admin.setEmail("admin@helpdesk.com");
        admin.setSenha(passwordEncoder.encode("123456"));
        admin.setProfile(PerfilEnum.ROLE_ADMIN);

        Usuario find = usuarioRepository.findByEmail("admin@helpdesk.com");
        if (find == null) {
        	usuarioRepository.save(admin);
        }
    }

}
