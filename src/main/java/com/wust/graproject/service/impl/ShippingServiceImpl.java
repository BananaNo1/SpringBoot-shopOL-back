package com.wust.graproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.wust.graproject.common.UserContext;
import com.wust.graproject.entity.Shipping;
import com.wust.graproject.entity.User;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.mapper.ShippingMapper;
import com.wust.graproject.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/18 21:41
 */

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private UserContext userContext;

    @Override
    public ResultDataDto list(int pageNum, int pageSize) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(user.getId());
        PageInfo pageInfo = new PageInfo(shippingList);
        return ResultDataDto.operationSuccess(pageInfo);
    }

    @Override
    public ResultDataDto select(Integer shippingId) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        Shipping shipping = shippingMapper.selectByUserIdAndShippingId(user.getId(), shippingId);
        if (shipping == null) {
            return ResultDataDto.operationErrorByMessage("该地址不存在");
        }
        return ResultDataDto.operationSuccess().setData(shipping);
    }

    @Override
    public ResultDataDto add(Shipping shipping) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        shipping.setUserId(user.getId());
        int count = shippingMapper.insert(shipping);
        if (count > 0) {
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return ResultDataDto.operationSuccess("添加地址成功", result);
        }
        return ResultDataDto.operationErrorByMessage("添加地址失败");
    }

    @Override
    public ResultDataDto update(Shipping shipping) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        shipping.setUserId(user.getId());
        int count = shippingMapper.updateByShipping(shipping);
        if (count > 0) {
            return ResultDataDto.operationSuccessByMessage("更新地址成功");
        }
        return ResultDataDto.operationErrorByMessage("更新地址失败");
    }

    @Override
    public ResultDataDto delete(Integer shippingId) {
        User user = userContext.getUser();
        if(user ==null){
            return ResultDataDto.operationNeedLogin();
        }
        int count = shippingMapper.deleteByShippingIdAndUserId(shippingId,user.getId());
        if(count > 0){
            return ResultDataDto.operationSuccessByMessage("删除地址成功");
        }
        return ResultDataDto.operationErrorByMessage("删除地址失败");
    }
}
