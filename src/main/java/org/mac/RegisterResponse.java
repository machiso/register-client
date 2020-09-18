package org.mac;

/**
 * 服务注册response
 */
public class RegisterResponse {

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
