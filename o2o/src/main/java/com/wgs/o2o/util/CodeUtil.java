package com.wgs.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
   public static boolean checkVerifyCode(HttpServletRequest request) {
	   String verifyCodeExpect=(String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
	   String verifyCodeAct=HttpServletRequestUtil.getString(request, "verifyCodeActual");
	   if(verifyCodeAct==null||!verifyCodeAct.equals(verifyCodeExpect)) {
		   return false;
	   }
	   return true;
   }
}
