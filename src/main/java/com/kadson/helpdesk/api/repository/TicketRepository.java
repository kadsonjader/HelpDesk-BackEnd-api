package com.kadson.helpdesk.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import com.kadson.helpdesk.api.entity.Ticket;

public interface TicketRepository extends MongoRepository<Ticket, String> {
	
   Page<Ticket> findByUsuarioIdOrderByDataDesc(Pageable pages,String userId);
	
	Page<Ticket> findByTituloIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingOrderByDataDesc(
			String titulo,String status,String prioridade,Pageable pages);
	
	Page<Ticket> findByTituloIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndUsuarioIdOrderByDataDesc(
			String titulo,String status,String prioridade,String usuarioId,Pageable pages);
	
	Page<Ticket> findByNumero(Integer numero,Pageable pages);
	
	Page<Ticket> findByTituloIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndAssignUserOrderByDataDesc(
			String titulo,String status,String prioridade,String assignUser,Pageable pages);
	
	

}
