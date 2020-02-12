package com.portal.radius;

import com.portal.domain.VpnConfigDomain;
import com.portal.redis.RedisClient;
import com.portal.service.RadiusPushAuth;

import com.portal.service.VpnConfigService;
import com.portal.utils.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tinyradius.attribute.RadiusAttribute;
import org.tinyradius.packet.AccessRequest;
import org.tinyradius.packet.RadiusPacket;
import org.tinyradius.util.RadiusException;
import org.tinyradius.util.RadiusServer;

import java.net.InetSocketAddress;

/**
 * @Author: zt
 * @Date: 2019-08-07 14:46
 */
public class CipherRadius extends RadiusServer {

    private static Logger logger = LoggerFactory.getLogger(CipherRadius.class);

    //vpn的ip地址
    private static final String VPN_IP = "VPN_IP_";


    private RedisClient redisClient = SpringBeanUtil.getBeanByClass(RedisClient.class);

    private VpnConfigService vpnConfigService = SpringBeanUtil.getBeanByClass(VpnConfigService.class);

    private RadiusPushAuth radiusPushAuth = SpringBeanUtil.getBeanByClass(RadiusPushAuth.class);

    private String USER_NAME = null;

//    @Override
//    public String getSharedSecret(InetSocketAddress inetSocketAddress) {
//        InetAddress address = inetSocketAddress.getAddress();
//        String vpnIpKey = (String) redisClient.get(VPN_IP + USER_NAME);
//        logger.error("vpn shared key:{}", vpnIpKey);
//        VpnConfigDomain configByIp = vpnConfigService.getConfigByIp(vpnIpKey);
//        return configByIp.getShareKey();

//    }

    @Override
    public String getSharedSecret(InetSocketAddress client) {
        return null;
    }

    @Override
    public String getSharedSecret(InetSocketAddress client, RadiusPacket packet) {
        try {
            RadiusAttribute attribute = packet.getAttribute("NAS-IP-Address");
            String vpnIp = attribute.getAttributeValue();
            String userName = packet.getAttributeValue("User-Name");
            redisClient.put(VPN_IP + userName, vpnIp);
            logger.info("vpn ip is: {}", vpnIp);
            VpnConfigDomain configByIp = vpnConfigService.getConfigByIp(vpnIp);
            return configByIp.getShareKey();
        }catch (Exception e){
            logger.error("未获取到sharedSecret");
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getUserPassword(String userName) {
        try {
            String pwd = (String) redisClient.get(userName);
            String vpnIp = (String) redisClient.get(VPN_IP + userName);
            String s = radiusPushAuth.radiusAuth(userName, pwd, vpnIp);
            logger.info("该用户:{} 的密码:{}", userName, s);
            return s;
        } catch (Exception e) {
            logger.error("VPN多因素认证过程中发生异常:{}", e.getCause());
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public RadiusPacket accessRequestReceived(AccessRequest accessRequest, InetSocketAddress client) throws RadiusException {
        logger.info("设置用户名：{}", accessRequest.getUserName());
        redisClient.put(accessRequest.getUserName(), accessRequest.getUserPassword());
        String plaintext = this.getUserPassword(accessRequest.getUserName());
        int type = 3;
        if (plaintext != null && accessRequest.verifyPassword(plaintext)) {
            type = 2;
        }
        RadiusPacket answer = new RadiusPacket(type, accessRequest.getPacketIdentifier());
        this.copyProxyState(accessRequest, answer);
        return answer;
    }


}
