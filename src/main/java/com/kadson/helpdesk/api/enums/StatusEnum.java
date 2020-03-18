package com.kadson.helpdesk.api.enums;

public enum StatusEnum {
	
	Novo,
	Designado,
	Resolvido,
	Aprovado,
	Reprovado,
	Fechado;
	
	public static StatusEnum getStatus(String status) {
		switch(status) {
		case "Novo" : return Novo;
		case "Designado" : return Designado;
		case "Resolvido" : return Resolvido;
		case "Aprovado" : return Aprovado;
		case "Reprovado" : return Reprovado;
		case "Fechado" : return Fechado;
		default : return Novo;
		}
	}
}
