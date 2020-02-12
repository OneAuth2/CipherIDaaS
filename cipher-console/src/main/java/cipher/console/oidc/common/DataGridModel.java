package cipher.console.oidc.common;

public class DataGridModel implements java.io.Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 6273941337779149371L;
  private int               page;                                   // 当前页,名字必须为page
  private int               rows;                                   // 每页大小,名字必须为rows
  private String            sort;                                   // 排序字段
  private String            order;                                  // 排序规则

  private int               currentRow       = 0;                   // 当前行序号

  public int getCurrentRow() {
    currentRow = getPage() <= 1 ? 0 : ((getPage() - 1) * rows);
    return currentRow;
  }

  public void setCurrentRow(int currentRow) {
    this.currentRow = currentRow;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  @Override
  public String toString() {
    return "DataGridModel [page=" + page + ", rows=" + rows + ", sort=" + sort + ", order=" + order + ", currentRow=" + currentRow + "]";
  }

}
