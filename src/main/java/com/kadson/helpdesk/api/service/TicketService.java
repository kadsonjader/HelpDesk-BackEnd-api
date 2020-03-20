package com.kadson.helpdesk.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kadson.helpdesk.api.entity.ChangeStatus;
import com.kadson.helpdesk.api.entity.Ticket;

@Component
public interface TicketService {
		
	Ticket createOrUpdate(Ticket ticket);
	
	Optional<Ticket> findById(String id);
	
	void delete(String id);
	
	Page<Ticket> listTicket(int page, int count);
	
	ChangeStatus createChangeStatus(ChangeStatus changeStatus);
	
	Iterable<ChangeStatus> listChangeStatus(String ticketId);
	
	Page<Ticket> findByCurrentUsuario(int page, int count, String usuarioId);
	
	Page<Ticket> findByParameters(int page, int count, String titulo, String status, String prioridade);
	
	Page<Ticket> findByParametersAndCurrentUsuario(int page, int count, String titulo, String status, String prioridade, String usuarioId);
	
	Page<Ticket> findByNumero(int page, int count, Integer numero);
	
	Iterable<Ticket> findAll();
	
	Page<Ticket> findByParameterAndAssignedUsuario(int page, int count, String titulo, String status, String prioridade, String assignedUsuario);
}
