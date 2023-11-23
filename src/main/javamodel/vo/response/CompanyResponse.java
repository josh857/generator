package cn.fufeii.learn.test.mp.model.vo.response;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * COMPANY Response
 *
 * @author josh
 */
@Data
public class CompanyResponse {

    private Integer id;

    private Integer rang;

    private Date createtime;

    private BigDecimal totalMoney;

    private String companyName;

    private String companyNum;

    private String address;

}