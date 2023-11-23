package cn.fufeii.learn.test.mp.model.vo.response;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * TRANSACTION_LOG Response
 *
 * @author josh
 */
@Data
public class TransactionLogResponse {

    private Integer personId;

    private Date createtime;

    private BigDecimal transacMoney;

    private String accountNum;

    private String transacLog;

}