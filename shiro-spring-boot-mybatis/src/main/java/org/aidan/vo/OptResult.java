package org.aidan.vo;

/**
 * @author Aidan
 * @创建时间：2019/1/7 9:43 AM
 * @描述: 统一响应封装体
 */
public class OptResult {

    private static final int CODE_SUCCESS = 1;
    private static final int CODE_FAIL = 0;

    private int code;

    private String msg;

    private Object data;


    public OptResult() {
        this.code = CODE_SUCCESS;
    }

    public OptResult success() {
        this.code = CODE_SUCCESS;
        this.msg = "操作成功";
        return this;
    }

    public OptResult success(String msg) {
        this.code = CODE_SUCCESS;
        this.msg = msg;
        return this;
    }

    public OptResult fail() {
        this.code = CODE_FAIL;
        this.msg = "操作失败";
        return this;
    }

    public OptResult fail(String msg) {
        this.code = CODE_FAIL;
        this.msg = msg;
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OptResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
