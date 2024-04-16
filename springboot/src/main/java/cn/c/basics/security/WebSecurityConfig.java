package cn.c.basics.security;

import cn.c.basics.security.jwt.JwtRoleFilter;
import cn.c.basics.utils.SecurityUtil;
import cn.c.basics.parameter.NoAuthenticationProperties;
import cn.c.basics.parameter.CsxLoginProperties;
import cn.c.basics.security.jwt.AuthenticationFailHandler;
import cn.c.basics.security.jwt.AuthenticationSuccessHandler;
import cn.c.basics.security.jwt.CsxAccessDeniedHandler;
import cn.c.basics.security.permission.MyFilterSecurityInterceptor;
import cn.c.basics.security.validate.ImageValidateFilter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity 配置
 * @author 陈 
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CsxLoginProperties cLoginProperties;

    @Autowired
    private NoAuthenticationProperties noAuthenticationProperties;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailHandler authenticationFailHandler;

    @Autowired
    private CsxAccessDeniedHandler cAccessDeniedHandler;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    private ImageValidateFilter imageValidateFilter;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @ApiOperation(value = "SpringSecurity配置")
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = httpSecurity.authorizeRequests();
        for(String authenticationUrl : noAuthenticationProperties.getAuthentication()){
            expressionInterceptUrlRegistry.antMatchers(authenticationUrl).permitAll();
        }
        expressionInterceptUrlRegistry.and().formLogin()
                // 默认提示登陆的接口
                .loginPage("/c/common/needLogin")
                // 默认登陆的接口
                .loginProcessingUrl("/c/login")
                .permitAll()
                // 登陆成功
                .successHandler(authenticationSuccessHandler)
                // 登陆失败
                .failureHandler(authenticationFailHandler)
                .and()
                // IFrame跨域
                .headers().frameOptions().disable()
                .and()
                .logout()
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                // 请求的身份认证
                .authenticated()
                .and()
                // 跨域配置
                .cors().and()
                // 关闭跨域拦截
                .csrf().disable()
                // 配置JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 系统菜单权限拦截
                .exceptionHandling().accessDeniedHandler(cAccessDeniedHandler)
                .and()
                // 验证码过滤
                .addFilterBefore(imageValidateFilter, UsernamePasswordAuthenticationFilter.class)
                // 备用过滤器
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // JWT认证过滤
                .addFilter(new JwtRoleFilter(authenticationManager(), cLoginProperties, stringRedisTemplate, securityUtil));
    }
}
