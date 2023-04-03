package com.finalproject.StayFinderApi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "Post")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AccountId", nullable=false)
//	@JsonIgnore
	private Account account;
	
	@Column(nullable = false,columnDefinition = "mediumtext")
	private String title;
	
	@Column(columnDefinition = "longtext")
	private String content;
	
	@Column(name="NumberOfFavourites",nullable = false)
	private long numberOfFavourites;
	
	@Column(name="status",nullable = false)
	private int status;
	
	@Column(name="PostTime",columnDefinition = "datetime")
	private Date postTime;
	
//	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "HostelId")
//	 @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Hostel hostel;
	
	@JsonIgnore
	@OneToMany(mappedBy="post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;
	
	@JsonIgnore
	@OneToMany(mappedBy="post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Schedule> schedules;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "listFavouritePosts")
	List<Account> listAccountLiked;

	public void setListAccountLiked(List<Account> listAccountLiked) {
		if(listAccountLiked == null)
			this.listAccountLiked = null;
		else if (listAccountLiked != this.listAccountLiked) {
			this.listAccountLiked = listAccountLiked;
		}
	}
	
	public List<Account>  getListAccountLiked() {
		return this.listAccountLiked == null ? null : new ArrayList<Account>(this.listAccountLiked);
	}
}
