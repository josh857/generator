package cn.fufeii.learn.test.mp.model.vo.request;

import java.math.BigDecimal;
import java.util.Date;
import cn.fufeii.learn.test.mp.util.BasePageRequest;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Data;

/**
 * COMPANY Request
 *
 * @author josh
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompanyRequest extends BasePageRequest {

    private Integer id;

    private Integer rang;

    private Date createtime;

    private BigDecimal totalMoney;

    private String companyName;

    private String companyNum;

    private String address;

}