package com.finalproject.StayFinderApi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Position")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Position implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="PositionName",nullable = false,columnDefinition = "text")
	@Enumerated(EnumType.STRING)
	private PositionNameEnum positionName;
	
	@JsonIgnore
	@OneToMany(mappedBy="position", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Account> accounts;
	
	
	public List<Account> getAccounts(){
		return this.accounts == null ? null : new ArrayList<Account>(this.accounts);
	}
	
	public void setListFavouritePosts(List<Account> accounts) {
		if (accounts == null) {
			this.accounts = null;
		} else {
			this.accounts =  accounts;
		}
	}
	
}
