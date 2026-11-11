package com.oraola.usedcar.bean;

//管理员
public class Administrator {
    private int administratorId;        // 管理员唯一标识
    private String administratorname;   // 用户名
    private String password;            // 密码

    public Administrator() {
    }

    public Administrator(int administratorId, String administratorname, String password) {
        this.administratorId = administratorId;
        this.administratorname = administratorname;
        this.password = password;
    }

    public int getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(int administratorId) {
        this.administratorId = administratorId;
    }

    public String getAdministratorname() {
        return administratorname;
    }

    public void setAdministratorname(String administratorname) {
        this.administratorname = administratorname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}