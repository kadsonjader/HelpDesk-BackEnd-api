package com.kadson.helpdesk.api.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kadson.helpdesk.api.enums.PerfilEnum;

@Document
public class Usuario {
	
	@Id
	private String id;
	
	@Indexed(unique = true)
	@NotBlank(message = "Email é obrigatorio")
	@Email(message = "Email invalido")
	private String email;
	
	@NotBlank(message = "Senha é obrigatorio")
	@Size(min = 6)
	private String senha;
	
	
	private PerfilEnum profile;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public PerfilEnum getProfile() {
		return profile;
	}


	public void setProfile(PerfilEnum profile) {
		this.profile = profile;
	}
	
	
	

}
