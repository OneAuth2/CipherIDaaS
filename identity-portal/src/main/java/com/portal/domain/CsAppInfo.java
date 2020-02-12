package com.portal.domain;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * TODO:
 * create by liuying at 2019/9/27
 *
 * @author liuying
 * @since 2019/9/27 14:55
 */
public class CsAppInfo implements Serializable {


    private int type;
    private LoginWindow loginWindow;
    private LoginedWindow loginedWindow;
    private String defaultPath;
    private String registerTitle;
    private String relativePath;
    private String notFoundUrl;
    private String notFoundMsg;
    private Actions actions;
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setLoginWindow(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
    }
    public LoginWindow getLoginWindow() {
        return loginWindow;
    }

    public void setLoginedWindow(LoginedWindow loginedWindow) {
        this.loginedWindow = loginedWindow;
    }
    public LoginedWindow getLoginedWindow() {
        return loginedWindow;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }
    public String getDefaultPath() {
        return defaultPath;
    }

    public void setRegisterTitle(String registerTitle) {
        this.registerTitle = registerTitle;
    }
    public String getRegisterTitle() {
        return registerTitle;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
    public String getRelativePath() {
        return relativePath;
    }

    public void setNotFoundUrl(String notFoundUrl) {
        this.notFoundUrl = notFoundUrl;
    }
    public String getNotFoundUrl() {
        return notFoundUrl;
    }

    public void setNotFoundMsg(String notFoundMsg) {
        this.notFoundMsg = notFoundMsg;
    }
    public String getNotFoundMsg() {
        return notFoundMsg;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }
    public Actions getActions() {
        return actions;
    }


    public static class LoginWindow {

        private String title;
        private String type;
        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

        public LoginWindow(String title, String type) {
            this.title = title;
            this.type = type;
        }
    }


    public static class LoginedWindow {

        private String title;
        private String type;
        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

        public LoginedWindow(String title, String type) {
            this.title = title;
            this.type = type;
        }
    }


    public static class Success {

        private String value;
        private int type;
        private int nId;
        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public int getnId() {
            return nId;
        }

        public void setnId(int nId) {
            this.nId = nId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }



    public static class Fail {

        private int type;
        private int nId;
        private Integer sleep;
        private Integer needRefresh;
        private String value;
        private Integer count;
        private Integer sub;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getnId() {
            return nId;
        }

        public void setnId(int nId) {
            this.nId = nId;
        }

        public Integer getSleep() {
            return sleep;
        }

        public void setSleep(Integer sleep) {
            this.sleep = sleep;
        }

        public Integer getNeedRefresh() {
            return needRefresh;
        }

        public void setNeedRefresh(Integer needRefresh) {
            this.needRefresh = needRefresh;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getSub() {
            return sub;
        }

        public void setSub(Integer sub) {
            this.sub = sub;
        }
    }



    public static class Actions {


        private JSONObject request;
        private List<Success> success;
        private List<Fail> fail;
        public void setSuccess(List<Success> success) {
            this.success = success;
        }
        public List<Success> getSuccess() {
            return success;
        }

        public void setFail(List<Fail> fail) {
            this.fail = fail;
        }
        public List<Fail> getFail() {
            return fail;
        }

        public JSONObject getRequest() {
            return request;
        }

        public void setRequest(JSONObject request) {
            this.request = request;
        }
    }
}
