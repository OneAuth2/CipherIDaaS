package cipher.console.oidc.mongodb.domain;

import java.io.Serializable;

public class Authorities implements Serializable {

    private String role;

    private String _class;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }
}
