package cipher.console.oidc.domain.web;

import java.io.Serializable;

public class RuleInfo implements Serializable {
      private int condition;
      private int conditionName;
      private String paramName;

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getConditionName() {
        return conditionName;
    }

    public void setConditionName(int conditionName) {
        this.conditionName = conditionName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
