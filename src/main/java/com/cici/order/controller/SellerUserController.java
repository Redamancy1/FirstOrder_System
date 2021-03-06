package com.cici.order.controller;

import com.cici.order.config.ProjectUrlConfig;
import com.cici.order.constant.CookieConstant;
import com.cici.order.constant.RedisConstant;
import com.cici.order.enums.ResultEnum;
import com.cici.order.model.SellerInfo;
import com.cici.order.service.SellerService;
import com.cici.order.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户操作接口
 *
 * @author Redamancy
 * @version 1.0
 * @since jdk 1.8
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid")String openid,
                              HttpServletResponse response,
                              Map<String, Object> map){

        //1.openid参数与数据库中的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getCode());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        //2.设置 token 至 redis
        //key:token,value:openid
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);

        //3.设置 token 至 cookie
        //token:token值
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);



        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //1.从 cookie 中查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie != null){
            //2.清除 redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

            //3.清除 cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }

        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        //登录出后重新跳转到登录验证界面，后续有开发平台账号再修改跳到二维码扫描界面
        map.put("url","/sell/seller/main");
        return new ModelAndView("common/success",map);

    }

    /**
     * 由于暂时没有企业微信开放平台，采取账号密码登录方式
     * 暂时不采用二维码扫描登录方式
     *
     * @return 跳转到登录验证界面
     */
    @GetMapping("/main")
    public ModelAndView main(){
        return new ModelAndView("common/main");
    }
}
