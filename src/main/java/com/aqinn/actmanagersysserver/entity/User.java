package com.aqinn.actmanagersysserver.entity;

/**
 * 用户 - 实体类
 * @author Aqinn
 * @date 2020/12/19 7:47 PM
 */
public class User {

    private Long id;

    private String account;

    private String pwd;

    private String name;

    private String contact;

    // 性别 0: 女 1: 男
    private Integer sex;

    private String intro;

    public User(Long id, String account, String pwd, String name, String contact, Integer sex, String intro) {
        this.id = id;
        this.account = account;
        this.pwd = pwd;
        this.name = name;
        this.contact = contact;
        this.sex = sex;
        this.intro = intro;
    }

    public User(String account, String pwd, String name, String contact, int sex, String intro) {
        this.account = account;
        this.pwd = pwd;
        this.name = name;
        this.contact = contact;
        this.sex = sex;
        this.intro = intro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", sex=" + sex +
                ", intro='" + intro + '\'' +
                '}';
    }
}
