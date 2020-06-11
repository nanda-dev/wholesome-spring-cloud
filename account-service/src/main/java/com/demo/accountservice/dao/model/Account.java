package com.demo.accountservice.dao.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
public class Account {
	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private String phoneNum;
	private String status;
	@Column(updatable = false)
	private String createdBy;
	@Column(updatable = false)
	private LocalDateTime createdOn;
	@Column(insertable = false)
	private String modifiedBy;
	@Column(insertable = false)
	private LocalDateTime modifiedOn;
}
