package beer.o2.common.domain;

import beer.o2.common.enums.ResponseCode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:bomber
 * @Date:Created in 5:07 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
public class NormalResult<T> {

    private Integer code;

    private String msg;

    private T data;

    public NormalResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public NormalResult(ResponseCode e, T data) {
        this.code = e.getCode();
        this.msg = e.getMessage();
        this.data = data;
    }

    public NormalResult(ResponseCode e) {
        this.code = e.getCode();
        this.msg = e.getMessage();
    }

    public NormalResult(String e) {
        this.code = ResponseCode.ERROR.getCode();
        this.msg = e;
    }

    public static <T> NormalResult<T> success(T data) {
        return new NormalResult<>(ResponseCode.SUCCESS, data);
    }

    public static <T> NormalResult<T> success() {
        return new NormalResult<>(ResponseCode.SUCCESS);
    }

    public static <T> NormalResult<T> failed(ResponseCode e) {
        return new NormalResult<>(e);
    }

    public static <T> NormalResult<T> failed(String e) {
        return new NormalResult<>(e);
    }
}