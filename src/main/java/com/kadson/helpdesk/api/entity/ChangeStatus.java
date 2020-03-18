package com.kadson.helpdesk.api.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kadson.helpdesk.api.enums.StatusEnum;

@Document
public class ChangeStatus {
	
	@Id
	private String id;
	
	@DBRef
	private Ticket ticket;
	
	@DBRef
	private Usuario usuarioChange;
	
	private Date dataChangeStatus;
	private StatusEnum status;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
	
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	public Usuario getUsuarioChange() {
		return usuarioChange;
	}
	
	public void setUsuarioChange(Usuario usuarioChange) {
		this.usuarioChange = usuarioChange;
	}
	
	public Date getDataChangeStatus() {
		return dataChangeStatus;
	}
	
	public void setDataChangeStatus(Date dataChangeStatus) {
		this.dataChangeStatus = dataChangeStatus;
	}
	
	public StatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	
}
