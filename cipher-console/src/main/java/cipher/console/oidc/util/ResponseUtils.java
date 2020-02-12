package cipher.console.oidc.util;


import net.minidev.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * HttpServletResponse帮助类
 *
 * @author liufang
 */
public final class ResponseUtils {

	public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

	/**
	 * 发送文本。使用UTF-8编码。
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	/**
	 * 发送xml。使用UTF-8编码。
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 *
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void render(HttpServletResponse response, String contentType, String text) {
		String str = !StringUtils.isBlank(text) ? text : "无";
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(str);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void returnJson(HttpServletResponse response, String msg, boolean flag) {
		JSONObject json = new JSONObject();
		json.put("success", flag);
		json.put("msg", msg);
		render(response, "text/plain;charset=UTF-8", json.toString());
	}




	//操作失败,自定义返回信息
	public static void customFailueResponse(HttpServletResponse response, String msg) {
		JSONObject resultJson = new JSONObject();
		resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
		resultJson.put(ReturnJsonCode.RETURN_MSG, msg);
		ResponseUtils.renderJson(response, resultJson.toString());
	}

	//操作成功,自定义返回信息
	public static void customSuccessResponse(HttpServletResponse response, String msg) {
		JSONObject resultJson = new JSONObject();
		resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.SUCCESS.getCode());
		resultJson.put(ReturnJsonCode.RETURN_MSG, msg);
		ResponseUtils.renderJson(response, resultJson.toString());
	}



	//操作成功,自定义返回信息
	public static void customSuccessResponse(HttpServletResponse response, String msg,int id) {
		JSONObject resultJson = new JSONObject();
		resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.SUCCESS.getCode());
		resultJson.put("id",id);
		resultJson.put(ReturnJsonCode.RETURN_MSG, msg);
		ResponseUtils.renderJson(response, resultJson.toString());
	}
	public static void responseSuccess(HttpServletResponse response) {
		net.sf.json.JSONObject resultJson = new net.sf.json.JSONObject();
		resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.SUCCESS.getCode());
		resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.SUCCESS.getMsg());
		ResponseUtils.renderJson(response, resultJson.toString());
	}
	public static void responseFailure(HttpServletResponse response) {
		net.sf.json.JSONObject resultJson = new net.sf.json.JSONObject();
		resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
		resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.FAILURE.getMsg());
		ResponseUtils.renderJson(response, resultJson.toString());
	}
	public static void responseInfoExists(HttpServletResponse response) {
		net.sf.json.JSONObject resultJson = new net.sf.json.JSONObject();
		resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.INFO_EXISTS.getCode());
		resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.INFO_EXISTS.getMsg());
		ResponseUtils.renderJson(response, resultJson.toString());
	}
	public static void responseBeenApplied(HttpServletResponse response) {
		net.sf.json.JSONObject resultJson = new net.sf.json.JSONObject();
		resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.BEEN_APPLIED.getCode());
		resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.BEEN_APPLIED.getMsg());
		ResponseUtils.renderJson(response, resultJson.toString());
	}

}
