package cn.fufeii.generator.assets;

import lombok.Data;

/**
 * 封装普通响应结果
 *
 * @author FuFei
 */
@Data
public final class CommonResult<T> {

    private final int code;
    private final String msg;
    private final T data;

    private CommonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return success(0, null);
    }

    public static <T> CommonResult<T> success(T data) {
        return success(0, data);
    }

    public static <T> CommonResult<T> success(int code, T data) {
        return new CommonResult<>(code, null, data);
    }

    public static <T> CommonResult<T> fail(int code, String msg) {
        return new CommonResult<>(code, msg, null);
    }

}