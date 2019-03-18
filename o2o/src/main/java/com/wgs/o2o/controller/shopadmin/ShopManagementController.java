package com.wgs.o2o.controller.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgs.o2o.dto.ImageHolder;
import com.wgs.o2o.dto.ShopExecution;
import com.wgs.o2o.entity.Area;
import com.wgs.o2o.entity.PersonInfo;
import com.wgs.o2o.entity.Shop;
import com.wgs.o2o.entity.ShopCategory;
import com.wgs.o2o.enums.ShopstateEnum;
import com.wgs.o2o.service.AreaService;
import com.wgs.o2o.service.ShopCategoryService;
import com.wgs.o2o.service.ShopService;
import com.wgs.o2o.util.CodeUtil;
import com.wgs.o2o.util.HttpServletRequestUtil;
import com.wgs.o2o.util.ImageUtil;
import com.wgs.o2o.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopservice;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> modelmap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId <= 0) {// 如果前端没有传shopId过来的话我们在session那里获取
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				modelmap.put("redirect", true);// 重定向
				modelmap.put("url", "/o2o/shopadmin/shoplist");
			} else {
				Shop currentShop = (Shop) currentShopObj;   
				modelmap.put("redirect", false);// 重定向
				modelmap.put("shopId", currentShop.getShopId());
			}

		} else {//如果传shopId进来就把它放到session里
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelmap.put("redirect", false);// 重定向
		}
		return modelmap;
	}

	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelmap = new HashMap<String, Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setName("翁贵森");
		request.getSession().setAttribute("user", user);
		user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopservice.getShopList(shopCondition, 0, 100);
			modelmap.put("shopList", se.getShopList());
			modelmap.put("user", user);
			modelmap.put("success", true);
		} catch (Exception e) {
			modelmap.put("success", false);
			modelmap.put("errMsg", e.getMessage());
		}
		return modelmap;
	}

	@RequestMapping(value = "getShopById", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelmap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopservice.getShopById(shopId);
				List<Area> aList = areaService.getAreaList();
				modelmap.put("shop", shop);
				modelmap.put("areaList", aList);
				modelmap.put("success", true);
			} catch (Exception e) {
				modelmap.put("success", false);
				modelmap.put("error", e.getMessage());
			}
		} else {
			modelmap.put("success", false);
			modelmap.put("error", "shopId empty");
		}
		return modelmap;

	}

	@RequestMapping(value = "/getshopomotomfo")
	@ResponseBody
	private Map<String, Object> getShopmotomfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("error", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/registeshop", method = RequestMethod.POST)
	@ResponseBody // 将返回值装换为json对象返回给前端
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("error", "验证码错误");
			return modelMap;
		}
		// 接收并转化相应的参数，包括店铺信息以及图片信息

		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("error", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");

		} else {
			modelMap.put("success", false);
			modelMap.put("error", "上传图片不能为空");
			return modelMap;
		}
		// 注册店铺
		if (shop != null && shopImg != null) {
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se;
			try {
				ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				se = shopservice.addshop(shop, imageHolder);
				if (se.getState() == ShopstateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					// 该用户可操作商店列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if (shopList == null || shopList.size() == 0) {
						shopList = new ArrayList<Shop>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				} else {
					modelMap.put("success", false);

				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("error", e.getMessage());
			}

			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("error", "请输入店铺信息");
			return modelMap;
		}

		// 返回结果

	}

	@RequestMapping(value = "/modifyShop", method = RequestMethod.POST)
	@ResponseBody // 将返回值装换为json对象返回给前端
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("error", "验证码错误");
			return modelMap;
		}
		// 接收并转化相应的参数，包括店铺信息以及图片信息

		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("error", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求...
		if (commonsMultipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			// 根据 name 获取上传的文件...
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");

		}
		// 修改店铺
		if (shop != null && shop.getShopId() != null) {
			ShopExecution se;
			try {
				ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				if (shopImg == null) {
					se = shopservice.modifyShop(shop,null);
				} else {
					se = shopservice.modifyShop(shop,imageHolder);
				}
				if (se.getState() == ShopstateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);

				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("error", e.getMessage());
			}

			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("error", "请输入店铺Id");
			return modelMap;
		}

		// 返回结果

	}

	/*
	 * private static void inputStreamToFile(InputStream ins, File file) {
	 * FileOutputStream os = null; try { os = new FileOutputStream(file); int
	 * bytesRead = 0; byte[] buffer = new byte[1024]; while ((bytesRead =
	 * ins.read(buffer)) != -1) { os.write(buffer, 0, bytesRead); } } catch
	 * (Exception e) { throw new RuntimeException("调用inputStream异常" +
	 * e.getMessage()); } finally { try { if (os != null) { os.close(); } if (ins !=
	 * null) { ins.close(); } } catch (IOException e) { throw new
	 * RuntimeException("inputStreamToFile关闭异常" + e.getMessage()); } }
	 * 
	 * }
	 */
}
