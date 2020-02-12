package cipher.console.oidc.domain;

import cipher.console.oidc.common.DataGridModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;


/**
 * domain基类
 */
public class BaseDomain implements Serializable {

	private static final long serialVersionUID = -309739470838406168L;

	private DataGridModel pageData;

	private String uuid;

	private String companyId;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public DataGridModel getPageData() {
		return pageData;
	}

	public void setPageData(DataGridModel pageData) {
		this.pageData = pageData;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
