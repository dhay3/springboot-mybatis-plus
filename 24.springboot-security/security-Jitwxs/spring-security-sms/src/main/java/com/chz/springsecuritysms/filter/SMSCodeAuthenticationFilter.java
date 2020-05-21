package com.chz.springsecuritysms.filter;

import com.chz.springsecuritysms.config.component.SMSCodeAuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义filter拦截短信登入请求
 */
public class SMSCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_SMS_KEY = "mobile";
    //对应name== mobile
    private String mobileParameter = SPRING_SECURITY_FORM_SMS_KEY;
    private boolean postOnly = true;

    // ~ Constructors
    // ===================================================================================================

    public SMSCodeAuthenticationFilter() {
        //对应发送登入请求的url,交给spring security处理
        super(new AntPathRequestMatcher("/login/mobile", "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();

        SMSCodeAuthenticationToken authRequest = new SMSCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }


    protected void setDetails(HttpServletRequest request,
                              SMSCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    public void setMobileParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Mobile parameter must not be empty or null");
        this.mobileParameter = usernameParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }
}
