package com.cici.order.enums;

import lombok.Getter;

/**
 * TODO
 *  请求返回结果枚举
 * @author Redamancy
 * @version 1.0
 * @since jdk 1.8
 */
@Getter
public enum ResultEnum {

    PARAM_ERROR(1000,"参数不正确"),
   PRODUCT_NOT_EXIST(1001,"商品不存在"),
   PRODUCT_STOCK_ERROR(1002,"商品库存不足"),
    ORDER_NOT_EXIST(1003,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(1004,"订单详情不存在"),
    ORDER_STATUS_ERROR(1005,"订单状态不正确"),
    ORDER_UPDATE_FAIL(1006,"订单更新失败"),
    ORDER_DETAIL_EMPTY(1007,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(1008,"订单支付状态不正确"),
    CART_EMPTY(1009,"购物车为空"),
    ORDER_OWNER_ERROR(1020,"该订单不属于当前用户"),
    ;


    private Integer code;
    private String message;

    ResultEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}