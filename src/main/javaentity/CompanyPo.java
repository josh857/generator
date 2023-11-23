package cn.fufeii.learn.test.mp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * COMPANY
 *
 * @author josh
 */
@Data
@Entity
@IdClass(value = CompanyPoId.class)
@Table(name = "COMPANY")
public class CompanyPo  implements Persistable, Serializable {
 private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    @Column("id")
    private Integer id;
    /**
     * rang
     */
    @Column("rang")
    private Integer rang;
    /**
     * createtime
     */
    @Column("createtime")
    private Date createtime;
    /**
     * total_money
     */
    @Column("total_money")
    private BigDecimal totalMoney;
    /**
     * Company_name
     */
    @Id
    @Column("Company_name")
    private String companyName;
    /**
     * Company_num
     */
    @Id
    @Column("Company_num")
    private String companyNum;
    /**
     * address
     */
    @Column("address")
    private String address;

    @Override
        public boolean isNew() {
            return true;
        }
}