package cn.fufeii.generator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简单封装了 INFORMATION_SCHEMA.COLUMNS 对象
 *
 * @author FuFei
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDO {

    private String javaName;

    private String name;

    private String type;

    private String comment;

    private String primary;
    public ColumnDO(String name, String type, String comment,String primary) {
        this.name = name;
        this.type = type;
        this.comment = comment;
        this.primary=primary;
    }
}
