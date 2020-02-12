package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class MenuForm extends BaseDomain {
  private Integer resourceId;
  private String  resourceName;
  private String  permission;
  private String  resourceUrl;
  private Integer parent;
  private Integer displaySort;
  private String  displayType;
  private String  description;
  private String text;
  private String href;
  private List<MenuForm> nodes;
  private Integer state;

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public MenuForm() {
  }

  public MenuForm(MenuForm menuForm) {
    this.resourceId=menuForm.getResourceId();
    this.menuList= new ArrayList<MenuForm>();
  }

  private List<MenuForm> menuList;


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public List<MenuForm> getNodes() {
    return nodes;
  }

  public void setNodes(List<MenuForm> nodes) {
    this.nodes = nodes;
  }

  public List<MenuForm> getMenuList() {
    return menuList;
  }

  public void setMenuList(List<MenuForm> menuList) {
    this.menuList = menuList;
  }

  public String getPermission() {
    return permission;
  }
  public void setPermission(String permission) {
    this.permission = permission;
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
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
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

  @Override
  public String toString() {
    return "MenuForm{" +
            "resourceId=" + resourceId +
            ", resourceName='" + resourceName + '\'' +
            ", permission='" + permission + '\'' +
            ", resourceUrl='" + resourceUrl + '\'' +
            ", parent=" + parent +
            ", displaySort=" + displaySort +
            ", displayType='" + displayType + '\'' +
            ", description='" + description + '\'' +
            ", text='" + text + '\'' +
            ", href='" + href + '\'' +
            ", nodes=" + nodes +
            ", menuList=" + menuList +
            '}';
  }
}
