package com.finalproject.StayFinderApi.entity;

public enum PositionNameEnum {
	ROLE_ADMIN(1),
	ROLE_USER(2);
	private int value;
    private PositionNameEnum(int value) {
        this.value = value;
    }
    
    public int getValue()
    {
    	return this.value;
    }
	
}
