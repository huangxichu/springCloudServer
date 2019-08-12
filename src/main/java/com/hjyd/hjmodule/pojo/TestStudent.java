package com.hjyd.hjmodule.pojo;

import com.hjyd.base.BaseEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

@Data
@Table(name = "cloudengine.TEST_STUDENT")
public class TestStudent extends BaseEntity {

    @AssignID
    private String id;
    private Integer age;
    private Integer cId;
    private String cName;
    private String name;
    private String sex;
    private String status;

}
