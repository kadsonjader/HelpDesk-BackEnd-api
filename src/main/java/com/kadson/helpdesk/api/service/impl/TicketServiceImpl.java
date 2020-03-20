package com.kadson.helpdesk.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kadson.helpdesk.api.entity.ChangeStatus;
import com.kadson.helpdesk.api.entity.Ticket;
import com.kadson.helpdesk.api.repository.ChangeStatusRepository;
import com.kadson.helpdesk.api.repository.TicketRepository;
import com.kadson.helpdesk.api.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService{
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private ChangeStatusRepository changeStatusRepository;

	@Override
	public Ticket createOrUpdate(Ticket ticket) {
		// TODO Auto-generated method stub
		return this.ticketRepository.save(ticket);
	}

	@Override
	public Optional<Ticket> findById(String id) {
		return this.ticketRepository.findById(id);
	}

	@Override
	public void delete(String id) {
		this.ticketRepository.deleteById(id);
		
	}

	public Page<Ticket> listTicket(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRepository.findAll(pages);
	}

	@Override
	public ChangeStatus createChangeStatus(ChangeStatus changeStatus) {
		return this.changeStatusRepository.save(changeStatus);
	}

	@Override
	public Iterable<ChangeStatus> listChangeStatus(String ticketId) {
		return this.changeStatusRepository.findByTicketIdOrderByDataChangeStatusDesc(ticketId);
	}

	@Override
	public Page<Ticket> findByCurrentUsuario(int page, int count, String usuarioId) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRepository.findByUsuarioIdOrderByDataDesc(pages, usuarioId);
	}

	@Override
	public Page<Ticket> findByParameters(int page, int count, String titulo, String status, String prioridade) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRepository.findByTituloIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingOrderByDataDesc(titulo, status, prioridade, pages);
	}

	@Override
	public Page<Ticket> findByParametersAndCurrentUsuario(int page, int count, String titulo, String status,
			String prioridade, String usuarioId) {
		Pageable pages = PageRequest.of(page, count);
		return this.
				ticketRepository.findByTituloIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndUsuarioIdOrderByDataDesc(titulo, status, prioridade, usuarioId, pages);
	}

	@Override
	public Page<Ticket> findByNumero(int page, int count, Integer numero) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRepository.findByNumero(numero, pages);
	}

	@Override
	public Iterable<Ticket> findAll() {
		return this.ticketRepository.findAll();
	}

	@Override
	public Page<Ticket> findByParameterAndAssignedUsuario(int page, int count, String titulo, String status,
			String prioridade, String assignedUsuario) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRepository.findByTituloIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndAssignUserOrderByDataDesc(titulo, status, prioridade, assignedUsuario, pages);
	}
	
}
