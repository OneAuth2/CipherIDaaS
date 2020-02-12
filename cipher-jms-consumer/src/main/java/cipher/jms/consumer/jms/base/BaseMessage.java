package cipher.jms.consumer.jms.base;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class BaseMessage implements Serializable {
    public String toMessage(){
        return JSON.toJSONString(this);
    }
}
