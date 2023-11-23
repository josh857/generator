package cn.fufeii.generator.assets;

import lombok.Data;

import java.util.Collection;


/**
 * 封装分页响应结果
 *
 * @author FuFei
 */
@Data
public final class PageResult<T> {

    private int code;
    private String msg;
    private long total;

    private Collection<T> data;

    private PageResult(long total, Collection<T> data) {
        this.code = 0;
        this.msg = "成功";
        this.total = total;
        this.data = data;
    }

    public static <T> PageResult<T> success(long total, Collection<T> data) {
        return new PageResult<>(total, data);
    }

}