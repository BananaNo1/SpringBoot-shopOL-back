package com.wust.graproject.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wust.graproject.common.Const;
import com.wust.graproject.common.UserContext;
import com.wust.graproject.entity.Cart;
import com.wust.graproject.entity.Product;
import com.wust.graproject.entity.User;
import com.wust.graproject.entity.vo.CartProductVo;
import com.wust.graproject.entity.vo.CartVo;
import com.wust.graproject.global.ResponseCode;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.mapper.CartMapper;
import com.wust.graproject.repository.ProductEsRepository;
import com.wust.graproject.service.ICartService;
import com.wust.graproject.util.BigDecimalUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/11 10:33
 */

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private UserContext userContext;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductEsRepository esRepository;

    @Value("${imageHost}")
    private String imageHost;

    @Override
    public ResultDataDto list() {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        List<Cart> carts = cartMapper.selectCartByUserId(user.getId());
        List<CartProductVo> cartProductVos = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(carts)) {
            for (Cart cart : carts) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setUserId(user.getId());
                cartProductVo.setProductId(cart.getProductId());
                Product product = esRepository.findById(cart.getProductId()).get();
                if (product != null) {
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());

                    int buyLimitCount = 0;
                    if (product.getStock() >= cart.getQuantity()) {
                        buyLimitCount = cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);

                        Cart cartQuantity = new Cart();
                        cartQuantity.setQuantity(buyLimitCount);
                        cartQuantity.setId(cart.getId());
                        cartMapper.updateByPrimaryKeySelective(cartQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cart.getChecked());

                    if (cart.getChecked() == Const.Cart.CHECKED) {
                        cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                    }

                    cartProductVos.add(cartProductVo);
                }
            }
        }
        CartVo cartVo = new CartVo();
        cartVo.setCartProductVoList(cartProductVos);
        cartVo.setAllChecked(this.getAllCheckedStatus(user.getId()));
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setImageHost(imageHost);
        return ResultDataDto.operationSuccess(cartVo);
    }

    @Override
    public ResultDataDto getCartProductCount() {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationSuccess(0);
        }
        return ResultDataDto.operationSuccess(cartMapper.selectCartProductCount(user.getId()));
    }

    @Override
    public ResultDataDto addToCart(Integer count, Integer productId) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        if (productId == null || count == null) {
            return ResultDataDto.operationErrorByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdAndProductId(user.getId(), productId);
        if (cart == null) {
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setProductId(productId);
            cartItem.setUserId(user.getId());
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        } else {
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list();
    }

    @Override
    public ResultDataDto updateProduct(Integer count, Integer productId) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        Cart cart = cartMapper.selectCartByUserIdAndProductId(user.getId(), productId);
        if (cart != null) {
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKey(cart);
        return this.list();
    }

    @Override
    public ResultDataDto deleteProduct(String productIds) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productList)) {
            return ResultDataDto.operationErrorByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(user.getId(), productList);
        return this.list();
    }

    @Override
    public ResultDataDto selectOrUnSelect(Integer productId, Integer checked) {
        User user = userContext.getUser();
        if (user == null) {
            return ResultDataDto.operationNeedLogin();
        }
        cartMapper.checkedOrUncheckedProduct(user.getId(), productId, checked);
        return this.list();
    }

    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartStatusByUserId(userId) == 0;
    }

}
