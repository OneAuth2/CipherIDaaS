package cipher.console.oidc.controller.web;

import cipher.console.oidc.domain.web.ImageInfoDomain;
import cipher.console.oidc.service.ImageInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cipher.console.oidc.common.ReturnUtils.successResponse;

/**
 * Created by 95744 on 2018/7/30.
 */

@Controller
@RequestMapping(value = "/cipher/image")
@EnableAutoConfiguration
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageInfoService imageInfoService;


    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> indexPage() {

        List<ImageInfoDomain> list = imageInfoService.getImageList();
        return successResponse("imageList", list);

    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryData(HttpServletResponse response) {
        LOGGER.debug("Enter ImageController.queryData");
        Map<String, Object> map = new HashMap<>();
        try {
            List<ImageInfoDomain> list = imageInfoService.getImageList();
            map.put("return_code", 1);
            map.put("imageList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/getNewList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getNewList(HttpServletResponse response, ImageInfoDomain form) {
        LOGGER.debug("Enter ImageController.queryData");
        Map<String, Object> map = new HashMap<>();
        try {
            List<ImageInfoDomain> list = imageInfoService.getImageList();
            map.put("title", 1);
            map.put("id", 1);
            map.put("start", 0);
            map.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
