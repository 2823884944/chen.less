package cn.c.basics.redis;

import cn.c.basics.exception.CsxException;
import cn.c.basics.parameter.*;
import cn.c.basics.utils.IpInfoUtil;
import cn.c.data.entity.Setting;
import cn.c.data.service.ISettingService;
import cn.c.data.utils.CsxNullUtils;
import cn.c.data.vo.HttpIpSsoSetting;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 限流拦截器
 * @author 陈
 */
@Slf4j
@Component
public class LimitRaterInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CsxLoginProperties cLoginProperties;

    @Autowired
    private RedisRaterLimiter redisRaterLimiter;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private ISettingService iSettingService;

    private static final String OTHER_SETTING = "OTHER_SETTING";

    @ApiOperation(value = "查询系统黑名单配置")
    public HttpIpSsoSetting getHttpIpSsoSetting(){
        Setting s = iSettingService.getById(OTHER_SETTING);
        if(s != null && !CsxNullUtils.isNull(s.getValue())){
            return new Gson().fromJson(s.getValue(), HttpIpSsoSetting.class);
        }
        return null;
    }

    @Override
    @ApiOperation(value = "方法执行前过滤")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,@ApiParam(name = "响应的处理器") Object handler) throws Exception {
        String ip = ipInfoUtil.getRequestIpAddress(request);
        // 单IP限流判断
        if(cLoginProperties.getOneLimiting()) {
            boolean flag1 = redisRaterLimiter.getLimitFlag(ip,cLoginProperties.getOneLimitingSize(), cLoginProperties.getOneLimitingTime());
            if (!flag1) {
                throw new CsxException("您的请求过于频繁，请稍后再试！");
            }
        }
        // 全局限流判断
        if(cLoginProperties.getAllLimiting()){
            boolean flag2 = redisRaterLimiter.getLimitFlag("C_LIMIT_ALL",cLoginProperties.getAllLimitingSize(), cLoginProperties.getAllLimitingTime());
            if (!flag2) {
                throw new CsxException("系统已达到最大承载量，无法继续提供服务，请稍后再试！");
            }
        }
        // IP黑名单
        HttpIpSsoSetting os = getHttpIpSsoSetting();
        if(os!=null && !CsxNullUtils.isNull(os.getBlacklist())){
            String[] list = os.getBlacklist().split("\n");
            for(String item : list){
                if(Objects.equals(ip,item)) {
                    throw new CsxException("您已被禁止访问该系统，如有疑问请咨询管理员，谢谢！");
                }
            }
        }
        // 特定方法限流判断
        try {
            Method method = ((HandlerMethod) handler).getMethod();
            RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
            if (rateLimiter != null) {
                boolean flag3 = redisRaterLimiter.getLimitFlag(method.getName(), rateLimiter.limit(), rateLimiter.timeout());
                if (!flag3) {
                    throw new CsxException(method.getName() + "方法请求超限，请稍后再试");
                }
            }
        }catch (Exception e){}
        // 放行至下一个拦截器
        return true;
    }

    @Override
    @ApiOperation(value = "方法执行完后过滤")
    public void postHandle(HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndView modelAndView) {

    }

    @Override
    @ApiOperation(value = "请求返回后过滤")
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler, Exception ex) {
    }
}
