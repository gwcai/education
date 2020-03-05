package com.genius.coder.base.form;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.BindingResult;

import java.io.Serializable;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
public class BaseDataResponse implements Serializable, Cloneable {
    private static final int OK_CODE = 0;
    private static final int FAIL_CODE = 1;
    private static final int EMPTY_CODE = 2;
    private static final int VERIFICATION_FAIL_CODE = 3;
    @ApiModelProperty(
            value = "成功代码",
            notes = "0代表成功，1代表失败，2代表空"
    )
    private int code;
    private String msg;
    private String detail;
    private Object data;

    private BaseDataResponse(int code) {
        this.code = code;
    }

    public static BaseDataResponse ok() {
        return new BaseDataResponse(0);
    }

    public static BaseDataResponse fail() {
        return new BaseDataResponse(1);
    }

    public static BaseDataResponse empty() {
        return new BaseDataResponse(2);
    }

    public static BaseDataResponse verifyFail(BindingResult bindingResult) {
        BaseDataResponse dataResponse = new BaseDataResponse(3);
        dataResponse.setMsg("表单数据不符合规范!");
        dataResponse.setData(bindingResult.getAllErrors());
        return dataResponse;
    }

    public BaseDataResponse msg(String msg) {
        this.msg = msg;
        return this;
    }

    public BaseDataResponse detail(String detail) {
        this.detail = detail;
        return this;
    }

    public BaseDataResponse data(Object data) {
        this.data = data;
        return this;
    }

    public BaseDataResponse error(Exception e) {
        this.msg = "操作失败";
        this.detail = e.getMessage();
        return this;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getDetail() {
        return this.detail;
    }

    public Object getData() {
        return this.data;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setDetail(final String detail) {
        this.detail = detail;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseDataResponse)) {
            return false;
        } else {
            BaseDataResponse other = (BaseDataResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else {
                label49: {
                    Object this$msg = this.getMsg();
                    Object other$msg = other.getMsg();
                    if (this$msg == null) {
                        if (other$msg == null) {
                            break label49;
                        }
                    } else if (this$msg.equals(other$msg)) {
                        break label49;
                    }

                    return false;
                }

                Object this$detail = this.getDetail();
                Object other$detail = other.getDetail();
                if (this$detail == null) {
                    if (other$detail != null) {
                        return false;
                    }
                } else if (!this$detail.equals(other$detail)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseDataResponse;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getCode();
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $detail = this.getDetail();
        result = result * 59 + ($detail == null ? 43 : $detail.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "BaseDataResponse(code=" + this.getCode() + ", msg=" + this.getMsg() + ", detail=" + this.getDetail() + ", data=" + this.getData() + ")";
    }
}
