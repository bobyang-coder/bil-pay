package com.bob.jobcenter.model;

import java.io.Serializable;

/**
 * 调度任务
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/10/6
 */
public class ScheduleJob implements Serializable {
    private static final long serialVersionUID = -2483468455261193103L;
    public static final String JOB_DATA_KEY = "scheduleJob";


    private long id;    //任务ID
    private String name;//任务名称
    private String group;//任务分组
    private int status;//任务状态(1:启动:0关闭)
    private String cron;//cron表达式
    private String description;//描述
    private int isConcurrent;//任务是否可并发执行(0:可以,1:不可)
    private String beanClass;//任务执行时调用哪个类(全类名)
    private String beanName;//bean名称
    private String methodName;//任务调用的方法名
    private String createTime;//创建时间
    private String updateTime;//修改时间
    private String createUser;//创建人
    private String modifyUser;//修改人
    private long version;//版本号


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(int isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", status=" + status +
                ", cron='" + cron + '\'' +
                ", description='" + description + '\'' +
                ", isConcurrent=" + isConcurrent +
                ", beanClass='" + beanClass + '\'' +
                ", beanName='" + beanName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", version=" + version +
                '}';
    }

    public enum StatusEnum {
        CLOSE(0, "关闭"),
        RUNNING(1, "启动"),;

        private int code;
        private String name;

        StatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }

    public enum IsConcurrentEnum {
        CAN(0, "可以"),
        CANNOT(1, "不可以"),;

        private int code;
        private String name;

        IsConcurrentEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }
}
