package com.cici.order.utils;

import com.cici.order.vo.ResultVO;

/**
 * 封装http请求对外的返回的最外层对象工具类
 *
 * @author Redamancy
 * @version 1.0
 * @since jdk 1.8
 */
public class ResultVOUtil {
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
