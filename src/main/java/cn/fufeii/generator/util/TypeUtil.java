package cn.fufeii.generator.util;

import cn.fufeii.generator.config.ConfigHolder;
import cn.fufeii.generator.domain.ColumnDO;
import cn.fufeii.generator.domain.TableDO;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.log.StaticLog;

import java.util.Arrays;
import java.util.Objects;

/**
 * java实体字段类型和mysql字段类型映射工具类
 *
 * @author FuFei
 * @date 2021/7/2
 */
public final class TypeUtil {

    private static final String[] BOOLEAN_TYPES = {"tinyint(1)"};

    private static final String[] STRING_TYPES = {"varchar", "char", "text"};

    private static final String[] INTEGER_TYPES = {"int"};

    private static final String[] LONG_TYPES = {"bigint"};

    private static final String[] BIG_DECIMAL_TYPES = {"decimal"};

    private static final String[] DATE_TYPES = {"datetime", "date", "timestamp"};


    public static String getJavaType(String mysqlType) {
        if (BooleanUtil.isTrue(ConfigHolder.getRuleEntity().getTinyint1ToBool())) {
            if (Arrays.stream(BOOLEAN_TYPES).anyMatch(mysqlType::contains)) {
                return "Boolean";
            }
        }
        if (Arrays.stream(STRING_TYPES).anyMatch(mysqlType::contains)) {
            return "String";
        }
        if (Arrays.stream(LONG_TYPES).anyMatch(mysqlType::contains)) {
            return "Long";
        }
        if (Arrays.stream(INTEGER_TYPES).anyMatch(mysqlType::contains)) {
            return "Integer";
        }
        if (Arrays.stream(BIG_DECIMAL_TYPES).anyMatch(mysqlType::contains)) {
            return "BigDecimal";
        }
        if (Arrays.stream(DATE_TYPES).anyMatch(mysqlType::contains)) {
            if (ConfigHolder.getRuleEntity().getDatetimeToLdt()) {
                return "LocalDateTime";
            }
            return "Date";
        }
        StaticLog.warn("unrecognized mysql type [{}] , use [java.lang.Object] instead", mysqlType);
        return "Object";
    }

    public static String getImport(String javaType) {
        if ("Date".equals(javaType)) {
            return "java.util.Date";
        }
        if ("BigDecimal".equals(javaType)) {
            return "java.math.BigDecimal";
        }
        if ("LocalDateTime".equalsIgnoreCase(javaType)) {
            return "java.time.LocalDateTime";
        }
        return "";
    }

    public static Object getIdJavaType(TableDO tableDO) {
        String idName = ConfigHolder.getRuleEntity().getIdName();
        ColumnDO columnDO = tableDO.getColumnDOList().stream().filter(it -> idName.equalsIgnoreCase(it.getName())).findAny().orElse(null);
        return Objects.isNull(columnDO) ? "Long" : getJavaType(columnDO.getType());
    }

}
