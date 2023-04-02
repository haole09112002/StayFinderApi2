package com.finalproject.StayFinderApi.entity;

public enum PostStatusEnum {
	APPROVED (1),
	NOT_YET_APPROVED (2),
	NOT_APPROVED (0);
	private int value;
    private PostStatusEnum(int value) {
        this.value = value;
    }
    
    public int getValue()
    {
    	return this.value;
    }
}
