package cn.c.basics.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RuntimeException 异常处理类
 * @author 陈
 */
@Data
public class CsxException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MSG = "系统出现错误";

    @ApiModelProperty(value = "异常消息内容")
    private String msg;

    public CsxException(){
        super(DEFAULT_MSG);
        this.msg = DEFAULT_MSG;
    }

    public CsxException(String msg){
        super(msg);
        this.msg = msg;
    }
}
