package com.hjyd.util;

import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;

/**
 * @program：ServiceA
 * @description：用于自动生成pojo代码
 * @author：黄细初
 * @create：2019-04-28 16:31
 */
public class SqlManagerUtils {


    private static String driver = "com.mysql.jdbc.Driver";

    private static String url = "jdbc:mysql://192.168.10.99:3306/cloudengine?userUnicode=true&autoReconnect=true&rewriteBatchedStatements=TRUE&useSSL=false";

    private static String username = "ce_admin";

    private static String password = "abc123";

    public static void main(String[] args) throws Exception{

        ConnectionSource connectionSource = ConnectionSourceHelper.
                getSimple(driver,url,username,password);
        DBStyle mysql = new MySqlStyle();

        SQLLoader loader = new ClasspathLoader("/sql");

        UnderlinedNameConversion nc = new UnderlinedNameConversion();

        SQLManager sqlManager =
                new SQLManager(mysql,loader,connectionSource,nc,new Interceptor[]{new DebugInterceptor()});


//        sqlManager.genPojoCodeToConsole("TEST_CLASS");
//        sqlManager.genSQLTemplateToConsole("TEST_CLASS");
        GenConfig config = new GenConfig();
        config.preferBigDecimal(true);
        config.setBaseClass("com.hjyd.base.BaseEntity");
        sqlManager.genPojoCode("TEST_STUDENT","com.hjyd.hjmodule.pojo",config);
    }



}
