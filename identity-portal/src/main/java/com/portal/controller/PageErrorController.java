package com.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class PageErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(PageErrorController.class);

    private static PageErrorController pageErrorController;

    @Autowired
    private ErrorAttributes errorAttributes;

    private final static String ERROR_PATH = "/error";

    public PageErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public PageErrorController() {
        if (errorAttributes == null){
            pageErrorController = new PageErrorController(errorAttributes);
        }
    }

    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request) {
        Map<String, Object> map = getErrorAttributes(request, false);
        ModelAndView mav = null;
        Integer status = (Integer) map.get("status");
        if (status == 404){
            mav = new ModelAndView("error/404", map);
        } else if (status == 403){
            mav = new ModelAndView("error/403", map);
        } else if (status == 500){
            mav = new ModelAndView("error/500", map);
        } else {
            mav = new ModelAndView("greeting", map);
        }

        return mav;
    }




    /**
     * Supports other formats like JSON, XML
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(requestAttributes,includeStackTrace);
        String URL = request.getRequestURL().toString();
        map.put("URL", URL);
        logger.info("AppErrorController.method [error info]: status-" + map.get("status") +", request url-" + URL);
        return map;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                logger.error("Enter PageErrorController.getStatus but failed, statusCode=[{}] ..==", statusCode);
                logger.error(ex.getMessage(), ex);
            }
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
