package cn.fufeii.learn.test.mp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TRANSACTION_LOG
 *
 * @author josh
 */
@Data
@Entity
@IdClass(value = TransactionLogPoId.class)
@Table(name = "TRANSACTION_LOG")
public class TransactionLogPo  implements Persistable, Serializable {
 private static final long serialVersionUID = 1L;
    /**
     * Person_id
     */
    @Column("Person_id")
    private Integer personId;
    /**
     * createtime
     */
    @Column("createtime")
    private Date createtime;
    /**
     * transac_money
     */
    @Column("transac_money")
    private BigDecimal transacMoney;
    /**
     * Account_num
     */
    @Column("Account_num")
    private String accountNum;
    /**
     * transac_log
     */
    @Column("transac_log")
    private String transacLog;

    @Override
        public boolean isNew() {
            return true;
        }
}