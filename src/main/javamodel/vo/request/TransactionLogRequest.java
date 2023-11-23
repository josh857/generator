package cn.fufeii.learn.test.mp.model.vo.request;

import java.math.BigDecimal;
import java.util.Date;
import cn.fufeii.learn.test.mp.util.BasePageRequest;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Data;

/**
 * TRANSACTION_LOG Request
 *
 * @author josh
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransactionLogRequest extends BasePageRequest {

    private Integer personId;

    private Date createtime;

    private BigDecimal transacMoney;

    private String accountNum;

    private String transacLog;

}