package com.kadson.helpdesk.api.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kadson.helpdesk.api.enums.PrioridadeEnum;
import com.kadson.helpdesk.api.enums.StatusEnum;

@Document
public class Ticket {
	
	@Id
	private String id;
	
	@DBRef(lazy = true)
	private Usuario usuario;
	
	private Date data;	
	private String titulo;	
	private Integer numero;
	private StatusEnum status;
	private PrioridadeEnum prioridade;
	
	@DBRef(lazy = true)
	private Usuario assignUser;
	
	private String descricao;
	private String imagem;
	
	@Transient
	private List<ChangeStatus> alteracoes;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public StatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	public PrioridadeEnum getPrioridade() {
		return prioridade;
	}
	
	public void setPrioridade(PrioridadeEnum prioridade) {
		this.prioridade = prioridade;
	}
	
	public Usuario getAssignUser() {
		return assignUser;
	}
	
	public void setAssignUser(Usuario assignUser) {
		this.assignUser = assignUser;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public List<ChangeStatus> getAlteracoes() {
		return alteracoes;
	}
	
	public void setAlteracoes(List<ChangeStatus> alteracoes) {
		this.alteracoes = alteracoes;
	}
	
	
	
}
