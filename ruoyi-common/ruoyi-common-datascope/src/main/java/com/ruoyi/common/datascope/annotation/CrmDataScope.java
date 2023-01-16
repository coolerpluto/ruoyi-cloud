package com.ruoyi.common.datascope.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定制CRM
 * 数据权限过滤注解
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrmDataScope
{
    /**
     * 数据库实例名 默认: remote_db
     */
    public String dbAlias() default "remote_db";
    /**
     * 部门表的别名
     */
    public String deptAlias() default "";

    /**
     * 用户表的别名
     */
    public String userAlias() default "";

    /**
     * 权限字符（用于多个角色匹配符合要求的权限）默认根据权限注解@RequiresPermissions获取，多个权限用逗号分隔开来
     */
    public String permission() default "";
}
