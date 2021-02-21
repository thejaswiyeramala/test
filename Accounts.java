package com.wf.ibs.bootappsecure.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Accounts {

	@Id  // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // unique AI value 
	private Long actId;
	
	//uci as foreign key
	//@OneToMany(cascade=CascadeType.MERGE)
	@ManyToOne
	private Customer customer;
	private String accountType;
	private String status;
	private Date dateCreated;
	private Double balance;
	
	//actId to join in Transactions table
	@OneToMany(mappedBy = "accounts")
	private List<Transactions> transactions;
	
	//Service Provider table mapping
	/*@OneToMany(mappedBy = "accounts")
	private List<ServiceProvider> servProv;*/
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getActId() {
		return actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}

	/*public List<ServiceProvider> getServProv() {
		return servProv;
	}

	public void setServProv(List<ServiceProvider> servProv) {
		this.servProv = servProv;
	} */
	
}
