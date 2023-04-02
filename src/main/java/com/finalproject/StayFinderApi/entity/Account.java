package com.finalproject.StayFinderApi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Account")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(columnDefinition = "text")
	private String name;
	
	@Column(nullable = false,columnDefinition = "varchar(50)")
	private String username;
	
	@JsonIgnore
	@Column(nullable = false,columnDefinition = "varchar(50)")
	private String password;
	
	@Column
	private boolean gender;
	
	@Column(nullable = false)
	private int status;
	
	@Column
	private String avatarUrl;
	
	@Column(columnDefinition = "char(10)")
	private String phonenumber;
	
	@ManyToOne
    @JoinColumn(name="PositionId", nullable=false)
	private Position position;
	
	@JsonIgnore
	@OneToMany(mappedBy="account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts;
	
	@JsonIgnore
	@OneToMany(mappedBy="account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
	  name = "favourites_account_post", 
	  joinColumns = @JoinColumn(name = "AccountId"), 
	  inverseJoinColumns = @JoinColumn(name = "PostId"))
	List<Post> listFavouritePosts;

//	@JsonIgnore
	public Position getPosition() {
		return this.position;
	}
	
	public List<Post> getPosts(){
		return this.posts == null ? null : new ArrayList<Post>(this.posts);
	}
	
	public void setPosts(List<Post> posts) {
		if (posts == null) {
			this.posts = null;
		} else {
			this.posts =  Collections.unmodifiableList(posts);
		}
	}
	
	public List<Comment> getComments(){
		return this.comments == null ? null : new ArrayList<Comment>(this.comments);
	}
	
	public void setComments(List<Comment> comments) {
		if (comments == null) {
			this.comments = null;
		} else {
			this.comments =  Collections.unmodifiableList(comments);
		}
	}
	
	
	public List<Post> getListFavouritePosts(){
		return this.listFavouritePosts == null ? null : new ArrayList<Post>(this.listFavouritePosts);
	}
	
	public void setListFavouritePosts(List<Post> listFavouritePosts) {
		if (comments == null) {
			this.listFavouritePosts = null;
		} else {
			this.listFavouritePosts =listFavouritePosts;
		}
	}
}
