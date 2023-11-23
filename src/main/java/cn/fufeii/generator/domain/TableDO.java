package cn.fufeii.generator.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 简单封装了 INFORMATION_SCHEMA.TABLES 对象
 *
 * @author FuFei
 */
@Data
@AllArgsConstructor
public class TableDO {

    private String name;

    private String comment;

    private List<ColumnDO> columnDOList;

    public TableDO(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

}
