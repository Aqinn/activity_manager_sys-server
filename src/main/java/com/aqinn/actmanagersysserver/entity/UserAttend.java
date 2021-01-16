package com.aqinn.actmanagersysserver.entity;

/**
 * 用户签到 - 实体类
 * @author Aqinn
 * @date 2020/12/19 7:49 PM
 */
public class UserAttend {

    private Long uId;

    private Long attendId;

    private Long attendTime;

    private Integer attendType;

    public UserAttend(Long uId, Long attendId, Long attendTime, Integer attendType) {
        this.uId = uId;
        this.attendId = attendId;
        this.attendTime = attendTime;
        this.attendType = attendType;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getAttendId() {
        return attendId;
    }

    public void setAttendId(Long attendId) {
        this.attendId = attendId;
    }

    public Long getAttendTime() {
        return attendTime;
    }

    public void setAttendTime(Long attendTime) {
        this.attendTime = attendTime;
    }

    public Integer getAttendType() {
        return attendType;
    }

    public void setAttendType(Integer attendType) {
        this.attendType = attendType;
    }

    @Override
    public String toString() {
        return "UserAttend{" +
                "uId=" + uId +
                ", attendId=" + attendId +
                ", attendTime='" + attendTime + '\'' +
                ", attendType=" + attendType +
                '}';
    }
}
