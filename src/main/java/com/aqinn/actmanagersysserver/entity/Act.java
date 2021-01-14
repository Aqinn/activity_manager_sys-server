package com.aqinn.actmanagersysserver.entity;

/**
 * 活动 - 实体类
 * @author Aqinn
 * @date 2020/12/19 7:40 PM
 */
public class Act {

    private Long id;

    private Long uId;

    private Long code;

    private Long pwd;

    private String name;

    private String desc;

    private String location;

    private String time;

    // 活动状态 开启关闭 1: 未开始 2: 进行中 3: 已结束
    private Integer isOpen;

    public Act(Long id) {
        this.id = id;
    }

    public Act(Long id, Long uId, Long code, Long pwd, String name, String desc, String location, String time, Integer isOpen) {
        this.id = id;
        this.uId = uId;
        this.code = code;
        this.pwd = pwd;
        this.name = name;
        this.desc = desc;
        this.location = location;
        this.time = time;
        this.isOpen = isOpen;
    }

    public Act(Long uId, Long code, Long pwd, String name, String desc, String location, String time, Integer isOpen) {
        this.uId = uId;
        this.code = code;
        this.pwd = pwd;
        this.name = name;
        this.desc = desc;
        this.location = location;
        this.time = time;
        this.isOpen = isOpen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getPwd() {
        return pwd;
    }

    public void setPwd(Long pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public String toString() {
        return "Act{" +
                "id=" + id +
                ", uId=" + uId +
                ", code=" + code +
                ", pwd=" + pwd +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
