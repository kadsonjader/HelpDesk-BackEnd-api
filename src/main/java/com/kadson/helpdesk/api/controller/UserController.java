package com.kadson.helpdesk.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadson.helpdesk.api.service.UsuarioService;
import com.kadson.helpdesk.api.entity.Usuario;
import com.kadson.helpdesk.api.response.Response;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Usuario>> create(HttpServletRequest request, @RequestBody Usuario usuario,
			BindingResult result){
			Response<Usuario> response = new Response<Usuario>();
			try {
				validateCreateUsuario(usuario, result);
				if(result.hasErrors()) {
					result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
					return ResponseEntity.badRequest().body(response);
				}
				usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
				Usuario usuarioPersisted = (Usuario) usuarioService.createOrUpdate(usuario);
				response.setData(usuarioPersisted);
			} catch (DuplicateKeyException dE) {
				response.getErrors().add("E-mail registrado !");
				return ResponseEntity.badRequest().body(response);
			} catch (Exception e) {
				response.getErrors().add(e.getMessage());
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
	}
	
	private void validateCreateUsuario(Usuario usuario, BindingResult result) {
		if(usuario.getEmail() == null) {
			result.addError(new ObjectError("Usuario", "Email não informado"));
		}
	}
}
