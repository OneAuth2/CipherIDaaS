package cipher.console.oidc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: TK
 * @Date: 2018/11/29 11:04
 * 简单分页功能实现
 */
public class PageUtils {
    /*参数需要页面传入*/
    private Integer pageSize=10;//每页显式多少条记录
    private Integer currentPage=1;//当前页号

    /*参数需要从数据查询*/
    private Integer allRowsAmount=0;//总记录数
    private List<?> items;//记录集合

    /*这些参数由计算得出*/
    private Integer allPageAmount;//总页数
    private Integer currentPageStartRow=1;//当前页面的开始行
    private Integer currentPageEndRow;//当前页面的结束行
    private Integer firstPage=1;//首页的页号
    private Integer lastPage;//末页的页号
    private Integer prevPage;//上一页页号
    private Integer nextPage;//下一页页号
    private Integer startPageNum;//导航开始页号
    private Integer endPageNum;//导航结束页号
    private Integer maxPageAmount =10;//最多显示多少页
    public List<Integer> showPageNums =new ArrayList<Integer>();//要显示的页号

    public PageUtils() {
        super();

    }

    /*设置当前页*/
    public void setCurrentPage(int currentPage){
        if(currentPage <1){
            this.currentPage=1;
        }else{
            this.currentPage=currentPage;
        }
    }
    /*设置每页记录数，默认8条*/
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /*设置总记录数*/
    public void setAllRowsAmount(int allRowsAmount) {
        this.allRowsAmount = allRowsAmount;
    }
    /*设置分页内容*/
    public void setItems(List<?> items) {
        this.items = items;
    }
    /*设置导航页数量*/
    public void setMaxPageAmount(int maxPageAmount) {
        this.maxPageAmount = maxPageAmount;
    }

    public void calculatePage(){
        //计算总页数
        if(this.allRowsAmount % this.pageSize ==0){
            this.allPageAmount=this.allRowsAmount/this.pageSize;
        }else{
            this.allPageAmount=this.allRowsAmount/this.pageSize+1;
        }
        //设置首页
        this.firstPage=1;
        //设置末页
        this.lastPage=this.allPageAmount;
        if(this.currentPage *pageSize <allRowsAmount){
            this.currentPageEndRow =this.currentPage*pageSize;
            this.currentPageStartRow =(this.currentPage-1)*pageSize+1;
            if(this.currentPageStartRow <0){
                this.currentPageStartRow=1;
            }
        }else{
            this.currentPageEndRow =this.allRowsAmount;
            this.currentPageStartRow =(this.allPageAmount-1)*pageSize+1;
            if(this.currentPageStartRow <0){
                this.currentPageStartRow=1;
            }
        }
        //设置前一页
        if(this.currentPage >1){
            this.prevPage=this.currentPage-1;
        }else{
            this.prevPage=this.currentPage;
        }
        //设置下一页
        if(this.currentPage <this.lastPage){
            this.nextPage=this.currentPage+1;
        }else{
            this.nextPage=this.lastPage;
        }
        //计算数字导航页
        startPageNum =Math.max(this.currentPage-maxPageAmount/2, firstPage);
        endPageNum =Math.min(startPageNum+maxPageAmount, lastPage);
        if(endPageNum-startPageNum <maxPageAmount){
            startPageNum =Math.max(endPageNum -maxPageAmount , 1);
        }
        for(int i=startPageNum ;i<=endPageNum;i++){
            showPageNums.add(i);
        }
    }

    //以下get方法是对外提供的方法用来获取参数值
    public Integer getPageSize() {
        return pageSize;
    }
    public Integer getCurrentPage() {
        return currentPage;
    }
    public Integer getAllRowsAmount() {
        return allRowsAmount;
    }
    public List<?> getItems() {
        return items;
    }

    public Integer getAllPageAmount() {
        return allPageAmount;
    }

    public Integer getCurrentPageStartRow() {
        return currentPageStartRow;
    }

    public Integer getCurrentPageEndRow() {
        return currentPageEndRow;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public Integer getPrevPage() {
        return prevPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public Integer getStartPageNum() {
        return startPageNum;
    }

    public Integer getEndPageNum() {
        return endPageNum;
    }

    public Integer getMaxPageAmount() {
        return maxPageAmount;
    }

    public List<Integer> getShowPageNums() {
        return showPageNums;
    }

    @Override
    public String toString() {
        return "PageUtil [pageSize=" + pageSize + ", currentPage="
                + currentPage + ", allRowsAmount=" + allRowsAmount + ", 每页内容items="
                + items + ", allPageAmount=" + allPageAmount
                + ", currentPageStartRow=" + currentPageStartRow
                + ", currentPageEndRow=" + currentPageEndRow + ", firstPage="
                + firstPage + ", lastPage=" + lastPage + ", prevPage="
                + prevPage + ", nextPage=" + nextPage + ", startPageNum="
                + startPageNum + ", endPageNum=" + endPageNum + ", maxPageAmount="
                + maxPageAmount + ", 页号list=" + showPageNums + "]";
    }



}
