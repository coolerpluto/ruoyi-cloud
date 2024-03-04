package com.highgo.company.aspect;

import com.highgo.company.annotation.CompanyRequireAuth;
import com.highgo.company.domain.ApiToken;
import com.highgo.company.service.IApiTokenService;
import com.ruoyi.common.core.exception.GlobalException;
import com.ruoyi.common.core.utils.ServletUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.auth.AuthUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Aspect
@Component
public class CompanyRequireAuthAspect implements Ordered
{

    @Autowired
    private IApiTokenService apiTokenService;


    @Before("@annotation(companyRequireAuth)")
    public void doBefore(JoinPoint joinPoint, CompanyRequireAuth companyRequireAuth) throws Throwable
    {
        HttpServletRequest req = ServletUtils.getRequest();
        assert req != null;
        String authCode = req.getHeader("AuthorizationHG");
        if (StringUtils.isNotBlank(authCode))
        {
            if (checkByAuthCode(authCode, req.getRequestURI()))
            {
                throw new GlobalException("暂时未授权或权限被收回,请重新申请授权");
            }
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        checkByRequiresPermissions(signature);
    }

    private void checkByRequiresPermissions(MethodSignature signature)
    {
        Method method = signature.getMethod();
        // 仿 校验 @RequiresPermissions 注解
        CompanyRequireAuth requiresPermissions = method.getAnnotation(CompanyRequireAuth.class);
        if (requiresPermissions != null)
        {
            AuthUtil.checkPermiOr(requiresPermissions.value());
        }
    }



    private boolean checkByAuthCode(String authCode, String uri)
    {
        ApiToken tokenInfo = apiTokenService.selectInfoByToken(authCode);
        if (tokenInfo == null)
        {
            return true;
        }
        String[] scope = tokenInfo.getScope().split(";");
        boolean hashAuth = false;
        for (int i = 0; i < scope.length; i++)
        {
            if (Pattern.matches(scope[i], uri))
            {
                hashAuth = true;
                break;
            }
        }
        return !hashAuth;
    }

    @Override
    public int getOrder()
    {
        return 1;
    }
}
