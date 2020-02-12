package cipher.console.oidc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * AD中ou的上下级关系
 *
 * @Author: zt
 * @Date: 2019-06-10 08:46
 */
public class AdOuModel implements Serializable {

    /**
     * 本级ou的路径
     */
    private String rDN;

    /**
     * ou的全路径
     */
    private String dn;

    /**
     * 是否被选中
     */
    private boolean isSelect;


    private List<AdOuModel> nodes;

    public AdOuModel() {
    }

    public AdOuModel(String rDN, String dn) {
        this.rDN = rDN;
        this.dn = dn;
    }

    public AdOuModel(String rDN) {
        this.rDN = rDN;
    }

    public String getrDN() {
        return rDN;
    }

    public void setrDN(String rDN) {
        this.rDN = rDN;
    }


    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public List<AdOuModel> getNodes() {

        if (this.nodes == null) {
            this.nodes = new ArrayList<>();
        }

        return nodes;
    }

    public void setNodes(List<AdOuModel> nodes) {
        this.nodes = nodes;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }


    @Override
    public String toString() {
        return "AdOuModel{" +
                "rDN='" + rDN + '\'' +
                ", dn='" + dn + '\'' +
                ", isSelect=" + isSelect +
                ", nodes=" + nodes +
                '}';
    }
}
