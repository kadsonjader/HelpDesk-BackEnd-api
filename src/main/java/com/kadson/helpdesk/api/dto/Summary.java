package com.kadson.helpdesk.api.dto;

import java.io.Serializable;

public class Summary implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private Integer amountNovo;
	private Integer amountResolvido;
	private Integer amountAprovado;
	private Integer amountReprovado;
	private Integer amountDesignado;
	private Integer amountFechado;
	
	public Integer getAmountNovo() {
		return amountNovo;
	}
	public void setAmountNovo(Integer amountNovo) {
		this.amountNovo = amountNovo;
	}
	public Integer getAmountResolvido() {
		return amountResolvido;
	}
	public void setAmountResolvido(Integer amountResolvido) {
		this.amountResolvido = amountResolvido;
	}
	public Integer getAmountAprovado() {
		return amountAprovado;
	}
	public void setAmountAprovado(Integer amountAprovado) {
		this.amountAprovado = amountAprovado;
	}
	public Integer getAmountReprovado() {
		return amountReprovado;
	}
	public void setAmountReprovado(Integer amountReprovado) {
		this.amountReprovado = amountReprovado;
	}
	public Integer getAmountDesignado() {
		return amountDesignado;
	}
	public void setAmountDesignado(Integer amountDesignado) {
		this.amountDesignado = amountDesignado;
	}
	public Integer getAmountFechado() {
		return amountFechado;
	}
	public void setAmountFechado(Integer amountFechado) {
		this.amountFechado = amountFechado;
	}
	
	
	
	
}
