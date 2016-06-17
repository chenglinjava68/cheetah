package org.cheetah.reset;

import org.cheetah.commons.utils.Assert;

import java.io.Serializable;
import java.util.Collections;

/**
 * @author Max
 */
public class ApiResult implements Serializable {
    private static final long serialVersionUID = -8008500505708437150L;

    private int status;
    private String message;
    private Object result;

    ApiResult() {
    }

    ApiResult(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.result = builder.result;
    }

    /**
     * @return
     */
    public int status() {
        return status;
    }

    /**
     * @return
     */
    public String message() {
        return message;
    }

    /**
     * @return
     */
    public Object result() {
        return result;
    }

    /**
     * @return
     */
    public static Builder ok() {
        return new Builder(ApiConstants.OK);
    }

    /**
     * @return
     */
    public static Builder error() {
        return error(ApiConstants.API_ERROR);
    }

    /**
     * @param status
     * @return
     */
    public static Builder error(int status) {
        Assert.isTrue(status != ApiConstants.OK, "status must ne 0.");
        return new Builder(status);
    }

    public static class Builder {
        int status;
        String message = "Everything is ok.";
        Object result = Collections.emptyMap();

        Builder(int status) {
            this.status = status;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder result(Object result) {
            Assert.isTrue(status == ApiConstants.OK, "status must eq 0.");
            Assert.notNull(result, "result must not null.");
            this.result = result;
            return this;
        }

        public ApiResult build() {
            return new ApiResult(this);
        }
    }
}
