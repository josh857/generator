package cn.fufeii.generator.util;

import cn.fufeii.generator.config.ConfigHolder;
import cn.hutool.core.util.StrUtil;

/**
 * PathUtil
 *
 * @author FuFei
 * @date 2022/4/15
 */
public final class PathUtil {
    private static final String TOKEN = "${BPP}";

    /**
     * 解析路径
     */
    public static String parsePath(String path) {
        if (path.startsWith(TOKEN)) {
            return ConfigHolder.getRuleCommon().getBasePackagePath() + path.replace(TOKEN, StrUtil.EMPTY);
        }
        return path;
    }

}
