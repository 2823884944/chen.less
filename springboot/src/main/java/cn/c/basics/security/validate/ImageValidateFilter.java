package cn.c.basics.security.validate;

import cn.c.basics.utils.ResponseUtil;
import cn.c.basics.parameter.CaptchaProperties;
import cn.c.data.utils.CsxNullUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 验证码过滤
 * @author 陈
 */
@Configuration
public class ImageValidateFilter extends OncePerRequestFilter {

    @Autowired
    private CaptchaProperties captchaProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PathMatcher pathMatcher;

    private static final boolean RESPONSE_FAIL_FLAG = false;

    private static final int RESPONSE_CODE_FAIL_CODE = 500;

    @Override
    @ApiOperation(value = "验证码过滤")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Boolean filterFlag = false;
        for(String requestURI : captchaProperties.getVerification()){
            if(pathMatcher.match(requestURI, request.getRequestURI())){
                filterFlag = true;
                break;
            }
        }
        if(!filterFlag) {
            filterChain.doFilter(request, response);
            return;
        }
        String verificationCodeId = request.getParameter("captchaId");
        String userInputCode = request.getParameter("code");
        if(CsxNullUtils.isNull(userInputCode) || CsxNullUtils.isNull(verificationCodeId)){
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_CODE_FAIL_CODE,"验证码为空"));
            return;
        }
        String codeAnsInRedis = redisTemplate.opsForValue().get(verificationCodeId);
        if(CsxNullUtils.isNull(codeAnsInRedis)){
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_CODE_FAIL_CODE,"已过期的验证码，需要重新填写"));
            return;
        }
        if(!Objects.equals(codeAnsInRedis.toLowerCase(),userInputCode.toLowerCase())) {
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_CODE_FAIL_CODE,"验证码不正确"));
            return;
        }
        redisTemplate.delete(verificationCodeId);
        filterChain.doFilter(request, response);
    }
}