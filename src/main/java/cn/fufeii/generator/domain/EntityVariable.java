package cn.fufeii.generator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 封装生成实体的变量参数
 *
 * @author FuFei
 * @date 2021/6/27
 */
@Data
@AllArgsConstructor
public class EntityVariable {

    private List<ColumnDO> filedList;

    private Set<String> importList;

}
