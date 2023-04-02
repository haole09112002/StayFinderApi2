package com.finalproject.StayFinderApi.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String roomTypeName;
}
