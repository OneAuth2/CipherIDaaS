package cipher.jms.consumer.mapper;

import cipher.jms.consumer.domain.EmailInfoDomain;

public interface EmailInfoMapper {

    public EmailInfoDomain getEmailInfo(String companyId);
}
