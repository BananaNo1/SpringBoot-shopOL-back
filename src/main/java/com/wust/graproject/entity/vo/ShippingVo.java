package com.wust.graproject.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/26 15:10
 */

@Data
@Accessors(chain = true)
public class ShippingVo {
    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;
}
