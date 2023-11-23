package cn.fufeii.generator.config;

import java.util.stream.Stream;

/**
 * 生成模式
 * 即选择对应的模式，则会按照对应模式的模板生成
 *
 * @author FuFei
 * @date 2021/7/3
 */
public enum Mode {

    /**
     * mybatis-plus模式
     */
    MYBATIS_PLUS,

    /**
     * springboot-jpa模式
     */
    JPA;

    public static Mode getByName(String modeName) {
        return Stream.of(values()).filter(it -> it.name().equalsIgnoreCase(modeName)).findAny().orElse(null);
    }

}
