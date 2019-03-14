package com.wgs.o2o.enums;

public enum ProductCategoryStateEnum {
	 SUCCESS(1, "操作成功"), INNER_ERROR(-1001,"内部系统错误"), NUll_SHOPID(-1002, "shopid为空"),NULL(-1003,"空");
	private int state;
	private String stateInfo;

	private ProductCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的state值返回相应的enum值
	 */
	public static ProductCategoryStateEnum stateOf(int state) {
		for(ProductCategoryStateEnum productCategoryStateEnum:values()) {
			if(productCategoryStateEnum.getState()==state) {
				return productCategoryStateEnum;
			}
			
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
}
