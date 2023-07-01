package com.x.orders.api;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.x.base.exception.XueChengException;
import com.x.orders.model.dto.AddOrderDto;
import com.x.orders.model.dto.PayRecordDto;
import com.x.orders.model.po.XcPayRecord;
import com.x.orders.service.OrderService;
import com.xuecheng.orders.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2022/10/25 11:39
 */
@Api(value = "订单支付接口", tags = "订单支付接口")
@Slf4j
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;
    @Value("${pay.alipay.APP_ID}")
    String APP_ID;
    @Value("${pay.alipay.APP_PRIVATE_KEY}")
    String APP_PRIVATE_KEY;

    @Value("${pay.alipay.ALIPAY_PUBLIC_KEY}")
    String ALIPAY_PUBLIC_KEY;

    @ApiOperation("生成支付二维码")
    @PostMapping("/generatepaycode")
    @ResponseBody
    public PayRecordDto generatePayCode(@RequestBody AddOrderDto addOrderDto) {
        //登录用户
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        //用户id
        String userId = user.getId();
        //1.创建商品订单
        //2.生成支付记录
        //3.生成二维码
        return orderService.createOrder(userId,addOrderDto);
    }



    @ApiOperation("扫码下单接口")
    @GetMapping("/requestpay")
    public void requestpay(String payNo, HttpServletResponse httpResponse) throws IOException, AlipayApiException {
        //校验payNo交易号是否存在
        XcPayRecord payRecord = orderService.getpayRecordByPayno(payNo);
        if(payRecord == null){
            XueChengException.cast("支付记录交易号不存在");
        }

        AlipayClient client = new DefaultAlipayClient(com.xuecheng.orders.config.AlipayConfig.URL, APP_ID, APP_PRIVATE_KEY, com.xuecheng.orders.config.AlipayConfig.FORMAT, com.xuecheng.orders.config.AlipayConfig.CHARSET, ALIPAY_PUBLIC_KEY, com.xuecheng.orders.config.AlipayConfig.SIGNTYPE);//获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
//        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        //告诉支付宝支付结果通知的地址
        alipayRequest.setNotifyUrl("http://tjxt-user-t.itheima.net/xuecheng/orders/paynotify");//在公共参数中设置回跳和通知地址
        //填充业务参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + payNo + "\"," + //商户网站唯一订单号，本项目指定支付记录的交易号
                "    \"total_amount\":"+ payRecord.getTotalPrice() + "," +
                "    \"subject\":\""+ payRecord.getTotalPrice() + "\"," +
                "    \"product_code\":\"QUICK_WAP_WAY\"" +
                "  }");//填充业务参数
        String form = client.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        httpResponse.setContentType("text/html;charset=" + com.xuecheng.orders.config.AlipayConfig.CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
    }
}

