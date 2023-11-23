package cn.fufeii.generator.assets;


import lombok.Data;

/**
 * 基础分页请求
 *
 * @author FuFei
 */
@Data
public class BasePageRequest {


    private int page = 1;


    private int size = 10;

}