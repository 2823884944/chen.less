package cn.c.basics.security;

import cn.c.basics.exception.CsxException;
import cn.c.data.entity.User;
import cn.c.data.service.IUserService;
import cn.c.data.utils.CsxNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 根据账号/手机号查询用户所有信息
 * @author 陈
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserService iUserService;

    private static final String LOGIN_FAIL_DISABLED_PRE = "userLoginDisableFlag:";

    @Override
    @ApiOperation(value = "根据账号/手机号查询用户所有信息")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String loginFailFlag = LOGIN_FAIL_DISABLED_PRE + username;
        String value = redisTemplate.opsForValue().get(loginFailFlag);
        Long timeRest = redisTemplate.getExpire(loginFailFlag, TimeUnit.MINUTES);
        if(!CsxNullUtils.isNull(value)){
            throw new CsxException("试错超限，请您在" + timeRest + "分钟后再登");
        }
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.and(wrapper -> wrapper.eq("username", username).or().eq("mobile",username));
        userQw.orderByDesc("create_time");
        userQw.last("limit 1");
        return new SecurityUserDetails(iUserService.getOne(userQw));
    }
}
