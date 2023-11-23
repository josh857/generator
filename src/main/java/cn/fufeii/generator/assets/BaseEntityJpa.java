package cn.fufeii.generator.assets;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.Date;

/**
 * 基础实体类
 *
 * @author FuFei
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntityJpa implements Persistable<Long> {

    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "cn.fufeii.generator.config.SnowflakeIdGenerator")
    private Long id;

    @Version
    @Column
    private Integer version;

    @Column
    private Date createDateTime;

    @Column
    private Date updateDateTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

}
