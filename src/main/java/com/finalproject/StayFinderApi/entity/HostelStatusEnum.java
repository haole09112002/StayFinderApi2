package com.finalproject.StayFinderApi.entity;

public enum HostelStatusEnum {
	YES(1),NO(0);
	private int value;
    private HostelStatusEnum(int value) {
        this.value = value;
    }
    
    public int getValue()
    {
    	return this.value;
    }
}
