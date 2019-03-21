package com.wust.graproject.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Descirption
 * @author leis
 * @date 2019/3/12 9:57
 */

@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private boolean allChecked;
    private String imageHost;

}
