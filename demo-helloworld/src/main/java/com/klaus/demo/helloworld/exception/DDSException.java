package com.klaus.demo.helloworld.exception;


import com.klaus.fd.exception.AbstractException;
import com.klaus.fd.exception.ExceptionCode;

/**
 * @author Klaus
 */
public class DDSException extends AbstractException {

    public DDSException(Integer code, String message) {
        super(code, message);
    }

    public <T extends ExceptionCode> DDSException(T irCode) {
        super(irCode);
    }
}
