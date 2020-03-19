package com.kadson.helpdesk.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kadson.helpdesk.api.entity.ChangeStatus;

public interface ChangeStatusRepository extends MongoRepository<ChangeStatus , String> {
	
	Iterable<ChangeStatus> findByTicketIdOrderByDataChangeStatusDesc(String ticketId);
	
}
