package com.x.media.service;

import com.x.base.model.PageParams;
import com.x.base.model.PageResult;
import com.x.media.model.dto.QueryMediaParamsDto;
import com.x.media.model.po.MediaFiles;

/**
 * @description 媒资文件管理业务类
 * @author Mr.M
 * @date 2022/9/10 8:55
 * @version 1.0
 */
public interface MediaFileService {

 /**媒资文件查询方法
  * @param companyId 机构id
  * @param pageParams 分页参数
  * @param queryMediaParamsDto 查询条件
  * @return 数据
  */
 PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);
}
