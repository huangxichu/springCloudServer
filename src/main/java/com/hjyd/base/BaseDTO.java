package com.hjyd.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @program：ServiceA
 * @description：分页查询信息
 * @author：黄细初
 * @create：2019-05-05 09:21
 */
@Data
public class BaseDTO implements Serializable {

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页数据大小
     */
    private Integer pageSize;

}
