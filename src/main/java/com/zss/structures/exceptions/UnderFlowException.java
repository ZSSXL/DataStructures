package com.zss.structures.exceptions;

/**
 * @author ZSS
 * @date 2022/5/8 16:49
 * @desc 下溢异常
 */
@SuppressWarnings("unused")
public class UnderFlowException extends Exception{

    private final String message;

    public UnderFlowException() {
        this.message = "下溢异常";
    }

    public UnderFlowException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
