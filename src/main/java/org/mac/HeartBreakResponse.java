package org.mac;

/**
 * 心跳response
 */
public class HeartBreakResponse {

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
