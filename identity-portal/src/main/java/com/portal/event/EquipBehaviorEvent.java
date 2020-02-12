package com.portal.event;

import com.portal.domain.EquipBehaviorInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 插入设备审计日志自定义事件
 * @author cozi
 * @date 2019-09-25
 **/
public class EquipBehaviorEvent extends ApplicationEvent {

    private EquipBehaviorInfo equipBehaviorInfo;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public EquipBehaviorEvent(Object source, EquipBehaviorInfo equipBehaviorInfo) {
        super(source);
        this.equipBehaviorInfo = equipBehaviorInfo;
    }

    public EquipBehaviorInfo getEquipBehaviorInfo() {
        return equipBehaviorInfo;
    }

    public void setEquipBehaviorInfo(EquipBehaviorInfo equipBehaviorInfo) {
        this.equipBehaviorInfo = equipBehaviorInfo;
    }
}
