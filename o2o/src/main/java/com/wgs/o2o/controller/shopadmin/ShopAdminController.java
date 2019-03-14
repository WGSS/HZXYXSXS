package com.wgs.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="shopadmin",method=RequestMethod.GET)
public class ShopAdminController {
    @RequestMapping(value="/shopoperation")
	public String shopOperation() {
		return "/shop/shopoperation";//spring-mvc配置文件已经设置路径信息
	}
    @RequestMapping(value="/shoplist")
	public String shopList() {
		return "/shop/shoplist";//spring-mvc配置文件已经设置路径信息
	}
    @RequestMapping(value="/shopmanage")
   	public String shopManage() {
   		return "/shop/shopmanage";//spring-mvc配置文件已经设置路径信息
   	}
    @RequestMapping(value="/productcategorymanage")
   	public String productCategoryManage() {
   		return "/shop/productcategorymanage";//spring-mvc配置文件已经设置路径信息
   	}
    
}
