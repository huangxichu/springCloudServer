package com.hjyd.hjmodule.pojo;

import com.hjyd.base.BaseEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

@Data
@Table(name="cloudengine.TEST_CLASS")
public class TestClass extends BaseEntity {
	
	private Integer id ;

	/**
	 * 班级名称
	 */
	private String name ;

	private String status ;
	
	public TestClass() {}


}