package com.wgs.o2o.enums;

public enum ShopstateEnum {
	CHECK(0, "审核中"), OFFLINE(-1, "非法商铺"), SUCCESS(1, "操作成功"), PASS(2, "认证通过"), INNER_ERROR(-1001,
			"内部系统错误"), NUll_SHOPID(-1002, "shopid为空"),NULL(-1003,"空");
	private int state;
	private String stateInfo;

	private ShopstateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的state值返回相应的enum值
	 */
	public static ShopstateEnum stateOf(int state) {
		for(ShopstateEnum shopstateEnum:values()) {
			if(shopstateEnum.getState()==state) {
				return shopstateEnum;
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
