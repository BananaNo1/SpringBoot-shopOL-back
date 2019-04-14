package com.wust.graproject.service.impl;


import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wust.graproject.common.Const;
import com.wust.graproject.common.UserContext;
import com.wust.graproject.entity.*;
import com.wust.graproject.entity.dto.MqDto;
import com.wust.graproject.entity.vo.OrderItemVo;
import com.wust.graproject.entity.vo.OrderProductVo;
import com.wust.graproject.entity.vo.OrderVo;
import com.wust.graproject.entity.vo.ShippingVo;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.mapper.*;
import com.wust.graproject.rabbitmq.AlipayCallBackProducer;
import com.wust.graproject.service.IOrderService;
import com.wust.graproject.service.ISolrService;
import com.wust.graproject.util.BigDecimalUtil;
import com.wust.graproject.util.DateTimeUtil;
import com.wust.graproject.util.FastDfsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/18 21:45
 */

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {


    private static AlipayTradeService tradeService;

    static {

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    @Autowired
    private UserContext userContext;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private ISolrService solrService;

    @Autowired
    private AlipayCallBackProducer alipayCallBackProducer;


    @Value("${imageHost}")
    private String imageHost;

    @Override
    public ResultDataDto create(Integer shippingId) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        List<Cart> carts = cartMapper.selectCheckedCartByUserId(user.getId());
        ResultDataDto resultDataDto = this.getCartOrderItem(user.getId(), carts);
        if (!resultDataDto.isSuccess()) {
            return resultDataDto;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) resultDataDto.getData();
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);
        Order order = this.generateOrder(user.getId(), shippingId, payment);
        if (order == null) {
            return ResultDataDto.operationErrorByMessage("生成订单错误");
        }
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ResultDataDto.operationErrorByMessage("购物车为空");
        }
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        orderItemMapper.bulkInsert(orderItemList);
        this.reduceProductStockAndIncreSales(orderItemList);
        this.cleanCart(carts);

        OrderVo orderVo = generateOrderVo(order, orderItemList);
        return ResultDataDto.operationSuccess(orderVo);
    }

    @Override
    public ResultDataDto getOrderCartProduct() {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        OrderProductVo orderProductVo = new OrderProductVo();
        List<Cart> carts = cartMapper.selectCheckedCartByUserId(user.getId());
        ResultDataDto resultDataDto = this.getCartOrderItem(user.getId(), carts);
        if (!resultDataDto.isSuccess()) {
            return resultDataDto;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) resultDataDto.getData();
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(generateOrderItemVo(orderItem));
        }
        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setImageHost("");
        return ResultDataDto.operationSuccess(orderProductVo);
    }

    @Override
    public ResultDataDto getOrderList(int pageNum, int pageSize) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        PageHelper.offsetPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(user.getId());
        List<OrderVo> orderVoList = generateOrderVoList(orderList, user.getId());
        PageInfo result = new PageInfo(orderList);
        result.setList(orderVoList);
        return ResultDataDto.operationSuccess(result);
    }

    @Override
    public ResultDataDto detail(Long orderNo) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        Order order = orderMapper.selectByUserIdAndOrderNo(user.getId(), orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoAndUserId(orderNo, user.getId());
            OrderVo orderVo = generateOrderVo(order, orderItemList);
            return ResultDataDto.operationSuccess(orderVo);
        }
        return ResultDataDto.operationErrorByMessage("没有找到该订单");
    }

    @Override
    public ResultDataDto cancelOrder(Long orderNo) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        Order order = orderMapper.selectByUserIdAndOrderNo(user.getId(), orderNo);
        if (order == null) {
            return ResultDataDto.operationErrorByMessage("该订单不存在");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()) {
            return ResultDataDto.operationErrorByMessage("已付款，无法取消订单");
        }
        Order update = new Order();
        update.setId(order.getId());
        update.setStatus(Const.OrderStatusEnum.CANCELED.getCode());

        int count = orderMapper.updateByPrimaryKeySelective(update);
        if (count > 0) {
            return ResultDataDto.operationSuccess();
        }
        return ResultDataDto.operationErrorByMessage("更新失败");
    }

    @Override
    public ResultDataDto pay(Long orderNo) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        Map<String, String> resultMap = Maps.newHashMap();
        Order order = orderMapper.selectByUserIdAndOrderNo(user.getId(), orderNo);
        if (order == null) {
            return ResultDataDto.operationErrorByMessage("没有该订单");
        }
        resultMap.put("orderNo", order.getOrderNo().toString());

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("扫码支付,订单号:").append(outTradeNo).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";
        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";
        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";
        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";
        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");
        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";
        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoAndUserId(orderNo, user.getId());
        for (OrderItem orderItem : orderItemList) {
            GoodsDetail goods = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), new Double(100).doubleValue()).longValue(),
                    orderItem.getQuantity());
            goodsDetailList.add(goods);
        }

        // 创建条码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl("http://51wustzds.com/order/alipayCallBack")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);
        //http:51wustzds.com/order/alipayCallBack
        // 调用tradePay方法获取当面付应答
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝支付成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
//
//                File folder = new File(path);
//                if(!folder.exists()){
//                    folder.setWritable(true);
//                    folder.mkdirs();
//                }
                String qrPath = String.format("/qr-%s.png", response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);
                String qr = "";
                try {
                    qr = FastDfsUtil.upload(qrPath, "png");
                    log.info("qr**********" + qr);
                } catch (IOException e) {
                    log.error("上传二维码异常", e);
                } catch (MyException e) {
                    log.error("上传二维码异常", e);
                }
                String qrUrl = imageHost + qr.replace("M00", "");
                if (qrUrl.equals(imageHost)) {
                    return ResultDataDto.operationErrorByMessage("二维码上传错误");
                }
                resultMap.put("qrUrl", qrUrl);
                return ResultDataDto.operationSuccess(resultMap);
            case FAILED:
                log.error("支付宝支付失败!!!");
                return ResultDataDto.operationErrorByMessage("支付宝支付失败!!!");

            case UNKNOWN:
                log.error("系统异常，订单状态未知!!!");
                return ResultDataDto.operationErrorByMessage("系统异常，订单状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ResultDataDto.operationErrorByMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    @Override
    public ResultDataDto alipayCallBack(Map<String, String> params) {
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return ResultDataDto.operationErrorByMessage("非法订单");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ResultDataDto.operationSuccessByMessage("支付宝重复调用");
        }
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }
        MqDto mqDto = new MqDto();
        mqDto.setId(order.getId());
        mqDto.setOrderNo(order.getOrderNo());
        mqDto.setPlatformNumber(tradeNo);
        mqDto.setUserId(order.getUserId());
        mqDto.setPlatformStatus(tradeStatus);
        alipayCallBackProducer.sendMsg(mqDto);
        return ResultDataDto.operationSuccess();
    }

    @Override
    public ResultDataDto queryOrderPayStatus(Long orderNo) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        Order order = orderMapper.selectByUserIdAndOrderNo(user.getId(), orderNo);
        if (order == null) {
            return ResultDataDto.operationErrorByMessage("没有该订单");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ResultDataDto.operationSuccess();
        }
        return ResultDataDto.operationError();
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    private List<OrderVo> generateOrderVoList(List<Order> orderList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orderList) {
            List<OrderItem> orderItemList = Lists.newArrayList();
            if (userId == null) {
                orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
            } else {
                orderItemList = orderItemMapper.selectByOrderNoAndUserId(order.getOrderNo(), userId);
            }
            OrderVo orderVo = generateOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    private OrderVo generateOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.ONLINE_PAY.getValue());

        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping != null) {
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(generateShippingVo(shipping));
        }

        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = generateOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    private OrderItemVo generateOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }

    private ShippingVo generateShippingVo(Shipping shipping) {
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        return shippingVo;
    }

    private void cleanCart(List<Cart> carts) {
        for (Cart cart : carts) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    private void reduceProductStockAndIncreSales(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = solrService.searchById(orderItem.getProductId());
//             esRepository.findById(orderItem.getProductId()).get();
            product.setStock(product.getStock() - orderItem.getQuantity());
            if (product.getSold() == null) {
                product.setSold(0);
            }
            product.setSold(product.getSold() + orderItem.getQuantity());
            solrService.add(product);
//            esRepository.save(product);
        }
    }

    private Order generateOrder(Integer id, Integer shippingId, BigDecimal payment) {
        Order order = new Order();
        long orderNo = this.generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(id);
        order.setShippingId(shippingId);
        int insert = orderMapper.insert(order);
        if (insert > 0) {
            return order;
        }
        return null;
    }

    private long generateOrderNo() {
        long l = System.currentTimeMillis();
        return l + new Random().nextInt(100);
    }

    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    private ResultDataDto getCartOrderItem(Integer userId, List<Cart> carts) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(carts)) {
            return ResultDataDto.operationErrorByMessage("购物车为空");
        }

        for (Cart cartItem : carts) {
            OrderItem orderItem = new OrderItem();
            Product product = solrService.searchById(cartItem.getProductId());
            // esRepository.findById(cartItem.getProductId()).get();
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
                return ResultDataDto.operationErrorByMessage("产品" + product.getName() + "不是在售状态");
            }

            if (cartItem.getQuantity() > product.getStock()) {
                return ResultDataDto.operationErrorByMessage("产品" + product.getName() + "库存不足");
            }
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setProductName(product.getName());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity()));
            orderItemList.add(orderItem);
        }
        return ResultDataDto.operationSuccess(orderItemList);
    }
}
