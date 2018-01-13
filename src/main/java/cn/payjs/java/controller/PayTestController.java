package cn.payjs.java.controller;

import cn.payjs.java.config.PayJSConfig;
import cn.payjs.java.util.HttpInvoker;
import cn.payjs.java.util.SignUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PayTestController {
    @Autowired
    private PayJSConfig payJSConfig;

    @RequestMapping(value = "nativePay")
    public ModelAndView nativePay(Integer money){
        ModelAndView mv = new ModelAndView();
        Map<String,String> map = new HashMap<>();
        map.put("mchid", payJSConfig.getMchid());
        map.put("total_fee",""+money);
        String out_trade_no = "order"+System.currentTimeMillis();
        map.put("out_trade_no",out_trade_no);
        map.put("body","春节大礼包");
        map.put("notify_url", "https://payjs.cn/help/");//请注意，，该路径需要payjs服务器可以直接访问，且结果为200。测试地址不行，www.baidu.com也不行
        String md5 = SignUtil.sign(map, payJSConfig.getKey());
        map.put("sign", md5.toUpperCase());
        map.put("body", map.get("body"));
        map.put("notify_url", map.get("notify_url"));
        String result = HttpInvoker.readContentFromPost("https://payjs.cn/api/native", map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(null != jsonObject && jsonObject.containsKey("qrcode")){
            mv.addObject("qrcode", jsonObject.getString("qrcode"));
            mv.setViewName("result");
        } else {

        }
        return mv;
    }

    @RequestMapping(value = "/")
    public ModelAndView index() {

        return new ModelAndView("index");
    }

    @RequestMapping(value = "/order")
    public ModelAndView order(BigDecimal totalFee, String body) {
        ModelAndView mv = new ModelAndView("pay");

        String mchid = payJSConfig.getMchid();
        Integer total_fee = (totalFee.multiply(new BigDecimal(100)).intValue());
        String out_trade_no = "order"+System.currentTimeMillis();
        String notify_url = "https://payjs.cn/help/";//请注意，，该路径需要payjs服务器可以直接访问，且http状态码为200。测试地址不行，www.baidu.com也不行
        String callback_url = "https://payjs.cn/help/";

        Map<String,String> map = new HashMap<>();
        map.put("mchid", mchid);
        map.put("total_fee",""+total_fee);
        map.put("out_trade_no",out_trade_no);
        map.put("body",body);
        map.put("notify_url", notify_url);
        map.put("callback_url", callback_url);
        String md5 = SignUtil.sign(map, payJSConfig.getKey());

        String sign = md5.toUpperCase();

        mv.addObject("mchid", mchid);
        mv.addObject("total_fee", total_fee);
        mv.addObject("out_trade_no", out_trade_no);
        mv.addObject("body", body);
        mv.addObject("notify_url", notify_url);
        mv.addObject("callback_url", callback_url);
        mv.addObject("sign", sign);
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/notify/{outOrderNumber}")
    public Map<String,Object> payNotify(@PathVariable String outOrderNumber, String return_code, String total_fee,
                                        String out_trade_no, String payjs_order_id, String transaction_id, String time_end,
                                        String openid, String mchid, String sign){
        System.out.println("收到通知-----");
        System.out.println("outOrderNumber:"+outOrderNumber);
        System.out.println("return_code:"+return_code);
        System.out.println("total_fee:"+total_fee);
        System.out.println("out_trade_no:"+out_trade_no);
        System.out.println("payjs_order_id:"+payjs_order_id);
        System.out.println("transaction_id:"+transaction_id);
        System.out.println("time_end:"+time_end);
        System.out.println("openid:"+openid);
        System.out.println("mchid:"+mchid);
        System.out.println("sign:"+sign);
        return null;
    }
}
