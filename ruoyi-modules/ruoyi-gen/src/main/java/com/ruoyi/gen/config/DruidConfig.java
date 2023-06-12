package com.ruoyi.gen.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.ruoyi.common.core.enums.DataSourceType;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.gen.config.datasource.DynamicDataSource;
import com.ruoyi.gen.config.datasource.DynamicDataSourceUtil;
import com.ruoyi.gen.domain.GenDbSource;
import com.ruoyi.gen.util.DataSourceComposeUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DruidConfig {
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean()
    @ConfigurationProperties("spring.datasource")
    public DataSource masterDataSource() {
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        // 从数据库中直接读取数据库
        JdbcTemplate jdbcTemplate = new JdbcTemplate(masterDataSource);
        List<GenDbSource> dsList = jdbcTemplate.query("select * from gen_db_source gds where status = 'Y'", new Object[] {},
                new BeanPropertyRowMapper<GenDbSource>(GenDbSource.class));
        if (dsList != null && dsList.size() > 0) {
            for (GenDbSource item : dsList) {
                DruidDataSource dds = DataSourceComposeUtils.composeDruidDataSource(item);
                targetDataSources.put(DataSourceType.SLAVE.name() + Convert.toStr(item.getId()), dds);
            }
        }
        DynamicDataSourceUtil.setTargetDataSources(targetDataSources);
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }

    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterDataSource());
        return sqlSessionFactoryBean.getObject();
    }
}
