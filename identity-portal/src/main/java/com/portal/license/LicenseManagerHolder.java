package com.portal.license;/**
 * @author lqgzj
 * @date 2019-09-27
 */

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * @Author qiaoxi
 * @Date 2019-09-2710:55
 **/
public class LicenseManagerHolder {
    private static LicenseManager licenseManager;

    public static synchronized LicenseManager getLicenseManager(LicenseParam licenseParams) {
        if (licenseManager == null) {
            licenseManager = new LicenseManager(licenseParams);
        }
        return licenseManager;
    }
}
