package com.finalproject.StayFinderApi.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
@Table(name = "Comment")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PostId", nullable=false)
	private Post post;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AccountId", nullable=false)
	private Account account;
	
	@Column(columnDefinition = "longtext")
	private String content;
	
	@Column(name="CommentTime",columnDefinition = "datetime")
	private Date commentTime;
	
	@Column
	private String imageUrl;

	
}
