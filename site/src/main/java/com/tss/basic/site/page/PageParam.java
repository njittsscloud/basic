package com.tss.basic.site.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页参数
 * 
 * @author: MQG
 * @date: 2018/11/27
 */
@ApiModel("分页参数")
public class PageParam {

    @ApiModelProperty(value = "当前页", example = "1")
    private int pageNum;

    @ApiModelProperty(value = "每页记录数", example = "10")
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
