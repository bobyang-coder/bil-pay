## 1.修改配置文件
修改配置文件generator.properties中jdbc.driverLocation为本地jdbc驱动包

## 2.添加表

在generatorConfig.xml中找到，copy一份修改为要添加的表
```
<table tableName="custmember_datasource_day" domainObjectName="CustmemberDatasourceDay"
       enableCountByExample="false" enableUpdateByExample="false"
       enableDeleteByExample="false" enableSelectByExample="false"
       selectByExampleQueryId="false">
</table>
        ```


## 2.生成命令
执行maven命令:
```
mybatis-generator:generate -e
```