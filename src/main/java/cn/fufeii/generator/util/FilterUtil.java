package cn.fufeii.generator.util;

import cn.fufeii.generator.config.ConfigHolder;
import cn.fufeii.generator.domain.ColumnDO;
import cn.fufeii.generator.domain.TableDO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 过滤工具类
 *
 * @author e-Fei.Fu
 */
public final class FilterUtil {

    /**
     * 对原始表进行过滤
     */
    public static List<TableDO> filerTable(List<TableDO> originTableDOList) {
        List<String> filterIncludeList = ConfigHolder.getRuleEntity().getFilterInclude().stream()
                .filter(it -> !it.contains(StrUtil.DOT)).map(String::trim).collect(Collectors.toList());
        Boolean filterFlag = ConfigHolder.getRuleEntity().getFilterFlag();
        List<TableDO> tableDOList = originTableDOList;
        // 存在需要过滤的情况
        if (!filterIncludeList.isEmpty()) {
            tableDOList = originTableDOList.stream().filter(tableDO -> {
                if (filterFlag && filterIncludeList.stream().anyMatch(it -> tableDO.getName().equals(it))) {
                    StaticLog.warn(">>>>>>>>>>>>>>>>>>>>>>>>>> ignore table [{}] <<<<<<<<<<<<<<<<<<<<<<<<<<", tableDO.getName());
                    return false;
                }
                if (!filterFlag && filterIncludeList.stream().noneMatch(it -> tableDO.getName().equals(it))) {
                    StaticLog.warn(">>>>>>>>>>>>>>>>>>>>>>>>>> ignore table [{}] <<<<<<<<<<<<<<<<<<<<<<<<<<", tableDO.getName());
                    return false;
                }
                return true;
            }).collect(Collectors.toList());
        }
        return tableDOList;
    }

    /**
     * 过滤字段
     */
    public static List<ColumnDO> filerColumn(List<ColumnDO> originColumnDOList) {
        List<String> filterIncludeList = ConfigHolder.getRuleEntity().getFilterInclude().stream()
                .filter(it -> it.contains(StrUtil.DOT)).map(String::trim).collect(Collectors.toList());
        Boolean filterFlag = ConfigHolder.getRuleEntity().getFilterFlag();
        List<ColumnDO> columnDOList = originColumnDOList;
        // 字段类型只处理 忽略的情况
        if (filterFlag && CollUtil.isNotEmpty(filterIncludeList)) {
            columnDOList = columnDOList.stream().filter(it1 -> filterIncludeList.stream().noneMatch(it2 -> it2.split("\\.")[1].equals(it1.getName()))).collect(Collectors.toList());
        }
        return columnDOList;
    }

}
