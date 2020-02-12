package cipher.console.oidc.domain.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuTreeNodesDomain {

    public String getText() {
        return text;
    }

    public void setText() {
        this.text = resourceName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public List<MenuTreeNodesDomain> getNodes() {
        return nodes;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public void setNodes(List<MenuTreeNodesDomain> nodes) {
        this.nodes = nodes;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static Map<String, Object> getStateMap() {
        return stateMap;
    }

    public static void setStateMap(Map<String, Object> stateMap) {
        MenuTreeNodesDomain.stateMap = stateMap;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    private String text;
    private String href;
    private String resourceId;
    private Integer parent;
    private String resourceName;
    private List<MenuTreeNodesDomain> nodes;
    private String icon;
    private static Map<String, Object> stateMap = new HashMap<String, Object>();
    private Map<String, Object> state;
    public MenuTreeNodesDomain() {
        nodes = new ArrayList<MenuTreeNodesDomain>();
    }
    public MenuTreeNodesDomain(MenuForm menuForm) {
        this.resourceId = menuForm.getResourceId() + "";
        this.text = menuForm.getResourceName();
        this.resourceName = menuForm.getResourceName();
        this.nodes = new ArrayList<MenuTreeNodesDomain>();
    }
}
