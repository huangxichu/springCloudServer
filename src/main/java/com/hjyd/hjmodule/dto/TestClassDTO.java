package com.hjyd.hjmodule.dto;

import com.hjyd.base.BaseDTO;
import com.hjyd.util.StringUtils;
import lombok.Data;

/**
 * @program：ServiceA
 * @description：
 * @author：黄细初
 * @create：2019-05-05 09:24
 */

@Data
public class TestClassDTO extends BaseDTO {

    private String name ;

    private String status ;

    public void setName(String name) {
        this.name = StringUtils.isNotEmpty(name) ? name : null;
    }

    public void setStatus(String status) {
        this.status = StringUtils.isNotEmpty(status) ? status : null;
    }
}
