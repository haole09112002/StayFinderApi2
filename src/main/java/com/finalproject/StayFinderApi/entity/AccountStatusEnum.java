package com.finalproject.StayFinderApi.entity;

public enum AccountStatusEnum {
	ENABLE(1), DISTABLE(0);
	private int value;
    private AccountStatusEnum(int value) {
        this.value = value;
    }
    
    public int getValue()
    {
    	return this.value;
    }
}
