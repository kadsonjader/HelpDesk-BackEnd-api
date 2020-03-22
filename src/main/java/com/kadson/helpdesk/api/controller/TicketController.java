package com.kadson.helpdesk.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadson.helpdesk.api.dto.Summary;
import com.kadson.helpdesk.api.entity.ChangeStatus;
import com.kadson.helpdesk.api.entity.Ticket;
import com.kadson.helpdesk.api.entity.Usuario;
import com.kadson.helpdesk.api.enums.PerfilEnum;
import com.kadson.helpdesk.api.enums.StatusEnum;
import com.kadson.helpdesk.api.response.Response;
import com.kadson.helpdesk.api.security.jwt.JwtTokenUtil;
import com.kadson.helpdesk.api.service.TicketService;
import com.kadson.helpdesk.api.service.UsuarioService;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "*")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	protected JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('CLIENTE')")
	public ResponseEntity<Response<Ticket>> createOrUpdate(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result){
		Response<Ticket> response = new Response<Ticket>();		
		try {
			validateCreateTicket(ticket, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			ticket.setStatus(StatusEnum.getStatus("Novo"));
			ticket.setUsuario(userFromRequest(request));
			ticket.setData(new Date());
			ticket.setNumero(generateNumber());
			Ticket ticketPersisted = (Ticket) ticketService.createOrUpdate(ticket);
			response.setData(ticketPersisted);
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateCreateTicket(Ticket ticket, BindingResult result) {
		if(ticket.getTitulo() == null) {
			result.addError(new ObjectError("Ticket", "Titulo não informado"));
			return;
		}
	}
	
	
	public Usuario userFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String email = jwtTokenUtil.getUsernameFromToken(token);
		return usuarioService.findByEmail(email);
	}
	
	private Integer generateNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}
	
	@PutMapping()
	@PreAuthorize("hasAnyRole('CLIENTE')")
	public ResponseEntity<Response<Ticket>> update(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result){
		Response<Ticket> response = new Response<Ticket>();		
		try {
			validateUpdateTicket(ticket, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Optional<Ticket> ticketCurrentOptional = ticketService.findById(ticket.getId());
			Ticket ticketCurrent = ticketCurrentOptional.get();
			ticket.setStatus(ticketCurrent.getStatus());
			ticket.setUsuario(ticketCurrent.getUsuario());
			ticket.setData(ticketCurrent.getData());
			ticket.setNumero(ticketCurrent.getNumero());
			if(ticketCurrent.getAssignUser() != null){
				ticket.setAssignUser(ticketCurrent.getAssignUser());
			}
			Ticket ticketPersisted = (Ticket) ticketService.createOrUpdate(ticket);
			response.setData(ticketPersisted);
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateUpdateTicket(Ticket ticket, BindingResult result) {
		if(ticket.getId() == null) {
			result.addError(new ObjectError("Ticket", "Id não informado"));
			return;
		}
		if(ticket.getTitulo() == null) {
			result.addError(new ObjectError("Ticket", "Titulo não informado"));
			return;
		}
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CLIENTE','TECNICO')")
	public ResponseEntity<Response<Ticket>> findById(@PathVariable("id") String id){
		Response<Ticket> response = new Response<Ticket>();
		Optional<Ticket> ticketOptional = ticketService.findById(id);
		Ticket ticket = ticketOptional.get();
		if(ticket == null) {
			response.getErrors().add("Registro não encontrado id: "+id);
			return ResponseEntity.badRequest().body(response);
		}
		List<ChangeStatus> changes = new ArrayList<ChangeStatus>();
		Iterable<ChangeStatus> changesCurrent =  ticketService.listChangeStatus(ticket.getId());
		for (Iterator<ChangeStatus> iterator = changesCurrent.iterator(); iterator.hasNext();) {
			ChangeStatus changeStatus = iterator.next();
			changeStatus.setTicket(null);
			changes.add(changeStatus);
		}	
		ticket.setAlteracoes(changes);
		response.setData(ticket);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CLIENTE')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		Optional<Ticket> ticketOptional = ticketService.findById(id);
		Ticket ticket = ticketOptional.get();
		if (ticket == null) {
			response.getErrors().add("Registro não encontrado id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		ticketService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('CLIENTE','TECNICO')")
	public ResponseEntity<Response<Page<Ticket>>> findAll(HttpServletRequest request, @PathVariable("page") int page, @PathVariable("count") int count) {
		Response<Page<Ticket>> response = new Response<Page<Ticket>>();
		Page<Ticket> tickets = null;
		Usuario usuarioRequest = userFromRequest(request);
		if(usuarioRequest.getProfile().equals(PerfilEnum.ROLE_TECNICO)) {
			tickets = ticketService.listTicket(page, count);
		}else if(usuarioRequest.getProfile().equals(PerfilEnum.ROLE_CLIENTE)) {
			tickets = ticketService.findByCurrentUsuario(page, count, usuarioRequest.getId());
		}
		response.setData(tickets);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}/{numero}/{titulo}/{status}/{orioridade}/{assigned}")
	@PreAuthorize("hasAnyRole('CLIENTE','TECNICO')")
	public ResponseEntity<Response<Page<Ticket>>> findByParams(HttpServletRequest request, 
															   @PathVariable("page") int page, 
															   @PathVariable("count") int count,
															   @PathVariable("count") Integer numero,
															   @PathVariable("count") String titulo,
															   @PathVariable("count") String status,
															   @PathVariable("count") String prioridade,
															   @PathVariable("count") boolean assigned) {
		titulo = titulo.equals("uninformed") ? "" : titulo;
		status = status.equals("uninformed") ? "" : status;
		prioridade = prioridade.equals("uninformed") ? "" : prioridade;
		
		Response<Page<Ticket>> response = new Response<Page<Ticket>>();
		Page<Ticket> tickets = null;
		if(numero > 0) {
			tickets = ticketService.findByNumero(page, count, numero);
		} else {
			Usuario usuarioRequest = userFromRequest(request);
			if(usuarioRequest.getProfile().equals(PerfilEnum.ROLE_TECNICO)) {
				if(assigned) {
					tickets = ticketService.findByParameterAndAssignedUsuario(page, count, titulo, status, prioridade, usuarioRequest.getId());					
				} else {
					tickets = ticketService.findByParameters(page, count, titulo, status, prioridade);
				}
			} else if(usuarioRequest.getProfile().equals(PerfilEnum.ROLE_CLIENTE)) {
				tickets = ticketService.findByParametersAndCurrentUsuario(page, count, titulo, status, prioridade, usuarioRequest.getId());
			}
		}
		response.setData(tickets);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(value = "{id}/{status}")
	@PreAuthorize("hasAnyRole('CLIENTE','TECNICO')")
	public ResponseEntity<Response<Ticket>> changeStatus(
														@PathVariable("id") String id,
														@PathVariable("status") String status,
														HttpServletRequest request, 
														@RequestBody Ticket ticket,
														BindingResult result){
		Response<Ticket> response = new Response<Ticket>();
		try {
			validateChangeStatus(id, status, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Optional<Ticket> ticketCurrentOptional = ticketService.findById(id);
			Ticket ticketCurrent = ticketCurrentOptional.get();
			ticketCurrent.setStatus(StatusEnum.getStatus(status));
			if(status.equals("Assigned")) {
				ticketCurrent.setAssignUser(userFromRequest(request));
			}
			Ticket ticketPersisted = (Ticket) ticketService.createOrUpdate(ticketCurrent);
			ChangeStatus changeStatus = new ChangeStatus();
			changeStatus.setUsuarioChange(userFromRequest(request));
			changeStatus.setDataChangeStatus(new Date());
			changeStatus.setStatus(StatusEnum.getStatus(status));
			changeStatus.setTicket(ticketPersisted);
			ticketService.createChangeStatus(changeStatus);
			response.setData(ticketPersisted);
		}catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateChangeStatus(String id, String status, BindingResult result) {
		if(id == null || id.equals("")) {
			result.addError(new ObjectError("Ticket", "Id não informado"));
			return;
		}
		if(status == null || status.equals("")) {
			result.addError(new ObjectError("Ticket", "Status não informado"));
			return;
		}
	}
	
	@GetMapping(value = "/summary")
	public ResponseEntity<Response<Summary>> findChart() {
		Response<Summary> response = new Response<Summary>();
		Summary chart = new Summary();
		int amountNew = 0;
		int amountResolved = 0;
		int amountApproved = 0;
		int amountDisapproved = 0;
		int amountAssigned = 0;
		int amountClosed = 0;
		Iterable<Ticket> tickets = ticketService.findAll();
		if (tickets != null) {
			for (Iterator<Ticket> iterator = tickets.iterator(); iterator.hasNext();) {
				Ticket ticket = iterator.next();
				if(ticket.getStatus().equals(StatusEnum.Novo)){
					amountNew ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Resolvido)){
					amountResolved ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Aprovado)){
					amountApproved ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Reprovado)){
					amountDisapproved ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Designado)){
					amountAssigned ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Fechado)){
					amountClosed ++;
				}
			}	
		}
		chart.setAmountAprovado(amountNew);
		chart.setAmountResolvido(amountResolved);
		chart.setAmountAprovado(amountApproved);
		chart.setAmountReprovado(amountDisapproved);
		chart.setAmountDesignado(amountAssigned);
		chart.setAmountFechado(amountClosed);
		response.setData(chart);
		return ResponseEntity.ok(response);
	}
}
