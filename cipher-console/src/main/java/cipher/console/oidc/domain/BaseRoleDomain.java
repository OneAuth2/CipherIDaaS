package cipher.console.oidc.domain;

public abstract class BaseRoleDomain {
    public String type;
    public String text;

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BaseRoleDomain{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
