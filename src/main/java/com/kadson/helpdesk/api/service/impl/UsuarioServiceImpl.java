package com.kadson.helpdesk.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kadson.helpdesk.api.entity.Usuario;
import com.kadson.helpdesk.api.repository.UsuarioRepository;
import com.kadson.helpdesk.api.service.UsuarioService;

@Component
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	
	public Usuario findByEmail(String email) {
		return this.usuarioRepository.findByEmail(email);
	}

	
	public Usuario createOrUpdate(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}

	
	public Usuario findById(String id) {
		return this.usuarioRepository.findById(id).get();
	}

	
	public void delete(String id) {
		this.usuarioRepository.deleteById(id);
		
	}

	
	public Page<Usuario> findAll(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.usuarioRepository.findAll(pages);
	}

}
