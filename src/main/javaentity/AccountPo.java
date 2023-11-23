package cn.fufeii.learn.test.mp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ACCOUNT
 *
 * @author josh
 */
@Data
@Entity
@IdClass(value = AccountPoId.class)
@Table(name = "ACCOUNT")
public class AccountPo  implements Persistable, Serializable {
 private static final long serialVersionUID = 1L;
    /**
     * Person_id
     */
    @Id
    @Column("Person_id")
    private Integer personId;
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
     * Account_name
     */
    @Id
    @Column("Account_name")
    private String accountName;
    /**
     * Account_num
     */
    @Id
    @Column("Account_num")
    private String accountNum;

    @Override
        public boolean isNew() {
            return true;
        }
}