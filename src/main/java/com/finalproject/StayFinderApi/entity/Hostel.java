package com.finalproject.StayFinderApi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "Hostel")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Hostel implements Serializable {
	
	public Hostel(String name, int capacity, double area, String address, double rentPrice, double depositPrice,
			int status, String description, double electricPrice, double waterPrice) {
		this.name = name;
		this.capacity = capacity;
		this.area = area;
		this.address = address;
		this.rentPrice = rentPrice;
		this.depositPrice = depositPrice;
		this.status = status;
		this.description = description;
		this.electricPrice = electricPrice;
		this.waterPrice = waterPrice;
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false,columnDefinition = "text")
	private String name;
	
	@Column
	private int capacity;
	
	@Column
	private double area;
	
	@Column(nullable = false,columnDefinition = "text")
	private String address;
	
	@Column(name="RentPrice",nullable = false)
	private double rentPrice;
	
	@Column(name="DepositPrice")
	private double depositPrice;
	
	@Column(nullable = false)
	private int status;
	
	@Column(columnDefinition = "longtext")
	private String description;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="RoomTypeId", nullable=false)

	private RoomType roomtype;
	
	
	@Column(name="ElectricPrice")
	private double electricPrice;
	
	@Column(name="WaterPrice")
	private double waterPrice;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@PrimaryKeyJoinColumn
//	@JsonIgnore
	private Post post;
	
	@OneToMany(mappedBy="hostel", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images;
	
//	@JsonIgnore
	public RoomType getRoomtype() {
		return this.roomtype;
	}
	
//	@JsonIgnore
	public Post getPost() {
		return this.post;
	}

	public void setImages(List<Image> images) {
		if (images == null)
			this.images = null;
		else if (images != this.images) {
			this.images = images;
		}
	}
	
	public List<Image> getImages(){
		return this.images == null ? null : new ArrayList<Image>(this.images);
	}
	
}
