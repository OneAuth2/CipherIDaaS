package cipher.console.oidc.domain.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuInfoDomain implements Serializable {

    private Integer resourceId;
    private String  resourceName;
    private String  resourceUrl;
    private Integer parent;
    private Integer displaySort;
    private String  displayType;
    private List<MenuInfoDomain> menuList;


    public MenuInfoDomain() {
    }

    public MenuInfoDomain(MenuInfoDomain menuForm) {
        this.resourceId=menuForm.getResourceId();
        this.menuList= new ArrayList<MenuInfoDomain>();
    }


    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getDisplaySort() {
        return displaySort;
    }

    public void setDisplaySort(Integer displaySort) {
        this.displaySort = displaySort;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public List<MenuInfoDomain> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuInfoDomain> menuList) {
        this.menuList = menuList;
    }
}
