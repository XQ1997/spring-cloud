package com.x.orders.service;

import com.x.orders.model.dto.AddOrderDto;
import com.x.orders.model.dto.PayRecordDto;
import com.x.orders.model.dto.PayStatusDto;
import com.x.orders.model.po.XcPayRecord;

/**
 * @author Mr.M
 * @version 1.0
 * @description 订单服务接口
 * @date 2022/10/25 11:41
 */
public interface OrderService {

    /**
     * @description 创建商品订单
     * @param addOrderDto 订单信息
     * @return PayRecordDto 支付交易记录(包括二维码)
     * @author Mr.M
     * @date 2022/10/4 11:02
     */
    public PayRecordDto createOrder(String userId, AddOrderDto addOrderDto);

    /**查询支付交易记录
     * @param payNo  交易记录号
     * @return
     */
    public XcPayRecord getpayRecordByPayno(String payNo);

    /**
     * @description 保存支付宝支付结果
     * @param payStatusDto  支付结果信息
     * @return void
     * @author Mr.M
     * @date 2022/10/4 16:52
     */
    public void saveAliPayStatus(PayStatusDto payStatusDto);
}
