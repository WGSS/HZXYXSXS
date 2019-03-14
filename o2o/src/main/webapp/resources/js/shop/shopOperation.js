$(function() {
	var shopId = getQueryString('shopId');
	var isEdit = shopId ? true : false;
	var initUrl = '/o2o/shopadmin/getshopomotomfo';//后台初始化店铺信息返回给前台的地址
	var registShopUrl = '/o2o/shopadmin/registershop';//前端赋值后，后台注册店铺的地址
	var shopInfo = '/o2o/shopadmin/getShopById?shopId=' + shopId;
	var EditShopUrl = '/o2o/shopadmin/modifyShop';

	if (!isEdit) {
		getShopInitInfo();
	} else {
		getShopInfo(shopId);
	}

	function getShopInfo(shopId) {
		$.getJSON(shopInfo, function(data) {
			if (data.success) {
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '"selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				$('#area option[data-id="'+shop.area.areaId+'"]').attr('selected', 'selected');
			}

		});
	}

	function getShopInitInfo() {//初始化前端选择器
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$("#shop-category").html(tempHtml);
				$("#area").html(tempAreaHtml);
			}
		});

	}

	$("#submit").click(function() {//前端提交按钮后，后台赋值
		var shop = {};
		if(isEdit){
			shop.shopId=shopId;
		}
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		shop.shopCategory = {
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		shop.area = {
			areaId : $('#area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		var shopImg = $('#shop-img')[0].files[0];
		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		var verifyCodeActual = $('#j_kaptcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		$.ajax({//前端的值返回给后端处理
			url : (isEdit ? EditShopUrl : registShopUrl),
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功');
				} else {
					$.toast('提交失败' + data.errMsg);
				}
				$('#kaptcha_img').click();
			}
		});

	});

})
