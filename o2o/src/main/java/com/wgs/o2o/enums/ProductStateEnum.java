package com.wgs.o2o.enums;

public enum ProductStateEnum {
	SUCCESS(1, "操作成功"),  INNER_ERROR(-1001,"内部系统错误"), NUll_ProductID(-1002, "productid为空"),NULL(-1003,"空");
	private int state;
	private String stateInfo;
	
	private ProductStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
		
	}
	
	public static ProductStateEnum stateof(int state) {
		for(ProductStateEnum pEnum:values()) {
			if(pEnum.getState()==state) {
				return pEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	
	public String getStateInfo() {
		return stateInfo;
	}

	
	
}
