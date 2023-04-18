package com.x.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author hp
 */
@Data
@ToString
@ApiModel("分页参数类")
@NoArgsConstructor
@AllArgsConstructor
public class PageParams {

    @ApiModelProperty("当前页码默认值")
    public static final long DEFAULT_PAGE_CURRENT = 1L;

    @ApiModelProperty("每页记录数默认值")
    public static final long DEFAULT_PAGE_SIZE = 10L;

    @ApiModelProperty("当前页码")
    private Long pageNo = DEFAULT_PAGE_CURRENT;

    @ApiModelProperty("每页记录数默认值")
    private Long pageSize = DEFAULT_PAGE_SIZE;
}
