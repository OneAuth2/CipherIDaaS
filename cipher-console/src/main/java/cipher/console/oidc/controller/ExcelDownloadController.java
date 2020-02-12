package cipher.console.oidc.controller;

import cipher.console.oidc.domain.exceldomain.SubAccountExcel;
import cipher.console.oidc.domain.exceldomain.SubNoClientIdExcel;
import cipher.console.oidc.domain.exceldomain.UserInfoExcel;
import cipher.console.oidc.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/6/5 19:58
 */
@Controller
@RequestMapping(value = "/cipher/download")
public class ExcelDownloadController {

    private static final Logger LOGGER= LoggerFactory.getLogger(ExcelDownloadController.class);
    /**
     * 子账号模板表的导出
     * @param response
     */
    @RequestMapping(value = "/subaccount")
    public void subAccountExport(HttpServletResponse response){
        LOGGER.debug("enter ExcelDownloadController.subAccountExport,download subaccount excel");
        List<SubAccountExcel> list=new ArrayList<>();
        FileUtil.exportExcel(list,"子账号信息表","",SubAccountExcel.class,"subaccount.xls",response);
    }

    /**
     * 不带appclientId的子账号模板表的导出
     * @param response
     */
    @RequestMapping(value = "/subexport")
    public void SubWithoutAppclientIdExport(HttpServletResponse response){
        LOGGER.debug("enter ExcelDownloadController.SubWithoutAppclientIdExport,download subaccount excel");
        List<SubNoClientIdExcel> list=new ArrayList<>();
        FileUtil.exportExcel(list,"子账号信息表","",SubNoClientIdExcel.class,"subaccount.xls",response);
    }

    @RequestMapping(value = "/userexport")
    public void UserInfoExport(HttpServletResponse response){
        LOGGER.debug("enter ExcelDownloadController.UserInfoExport,download subaccount excel");
        List<UserInfoExcel> list=new ArrayList<>();
        FileUtil.exportExcel(list,"账号模板表","",UserInfoExcel.class,"account.xls",response);
    }


}
