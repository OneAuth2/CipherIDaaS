package cipher.console.oidc.domain.web;

public class CustomAttributeInfo {
    private String uuid;
    private String attributeName;
    private String attributeDescription;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeDescription() {
        return attributeDescription;
    }

    public void setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
    }

    @Override
    public String toString() {
        return "CustomAttributeInfo{" +
                "uuid='" + uuid + '\'' +
                ", attributeName='" + attributeName + '\'' +
                ", attributeDescription='" + attributeDescription + '\'' +
                '}';
    }
}
