package cn.fufeii.learn.test.mp.model.vo.response;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * ACCOUNT Response
 *
 * @author josh
 */
@Data
public class AccountResponse {

    private Integer personId;

    private Date createtime;

    private BigDecimal totalMoney;

    private String accountName;

    private String accountNum;

}