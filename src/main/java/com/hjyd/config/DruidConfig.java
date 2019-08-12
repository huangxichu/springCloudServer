package com.hjyd.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.assertj.core.util.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * @program：ServiceA
 * @description：数据源配置
 * @author：黄细初
 * @create：2019-04-28 14:25
 */

@Configuration
public class DruidConfig {

    /**
     * 将配置文件中spring.druid.*配置到DruidDataSource中
     * @param statFilter
     * @return
     * @throws SQLException
     */
    @ConfigurationProperties(prefix="spring.druid")
    @Bean(name="dataSource")
    public DataSource dataSource(Filter statFilter) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        //超过时间限制是否回收
//        dataSource.setRemoveAbandoned(true);
        //超时时间：单位为秒
//        dataSource.setRemoveAbandonedTimeout(180);
        //关闭Abandoned连接时输出的错误日志
//        dataSource.setLogAbandoned(true);
        return dataSource;
    }

    /**
     * 配置慢连接过滤
     * @return
     */
    @Bean
    public Filter statFilter(){
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(5000);
        filter.setLogSlowSql(true);
        filter.setMergeSql(true);
        return filter;
    }

    /**
     * 添加监控，可以帮助我们分析慢sql和每个sql的执行时间
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }


}
