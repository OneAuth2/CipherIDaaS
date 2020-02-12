package com.portal.domain;


import java.io.Serializable;

public class TeamInfo implements Serializable {

  private String dsgTeamId;

    public String getDsgTeamId() {
        return dsgTeamId;
    }

    public void setDsgTeamId(String dsgTeamId) {
        this.dsgTeamId = dsgTeamId;
    }
}
