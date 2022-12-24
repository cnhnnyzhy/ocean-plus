package com.ocean.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.admin.api.dto.rsp.TenantInfoRsp;
import com.ocean.auth.exception.BizErrorCode;
import com.ocean.auth.service.TokenService;
import com.ocean.client.factory.RemoteClientFactory;
import com.ocean.common.core.constant.SecurityConstants;
import com.ocean.common.core.dto.Result;
import com.ocean.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Token接口
 *
 * @author ocean
 * @date 2022/10/15
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {
    private final RemoteClientFactory remoteClientFactory;

    private final ClientDetailsService clientDetailsService;

    private final TokenService tokenService;

    /**
     * 认证页面
     *
     * @param modelAndView
     * @param error        表单登录失败处理回调的错误信息
     * @return
     */
    @GetMapping("/login")
    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        Result<List<TenantInfoRsp>> tenantInfoList = remoteClientFactory.getTenantRemoteClient().list(SecurityConstants.FROM_IN);
        modelAndView.setViewName("flt/login");
        modelAndView.addObject("error", error);
        modelAndView.addObject("tenantList", tenantInfoList);
        return modelAndView;
    }

    /**
     * 确认授权页
     *
     * @param request
     * @param session
     * @param modelAndView
     * @return
     */
    @GetMapping("/confirm_access")
    public ModelAndView confirm(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
        Map<String, Object> scopeList = (Map<String, Object>) request.getAttribute("scopes");
        modelAndView.addObject("scopeList", scopeList.keySet());

        Object auth = session.getAttribute("authorizationRequest");
        if (Objects.nonNull(auth)) {
            AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
            modelAndView.addObject("app", clientDetails.getAdditionalInformation());
            modelAndView.addObject("user", SecurityUtils.getLoginUser());
        }
        modelAndView.setViewName("ftl/confirm");
        return modelAndView;
    }

    /**
     * 退出token
     *
     * @param authorization
     * @return
     */
    @DeleteMapping("logout")
    public Result<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if (StringUtils.isBlank(authorization)) {
            return Result.error(BizErrorCode.TOKEN_IS_NULL);
        }
        String token = authorization.replace(OAuth2AccessToken.BEARER_TYPE, StringUtils.EMPTY).trim();
        return deleteToken(token);
    }

    /**
     * 删除token
     *
     * @param token
     * @return
     */
    @DeleteMapping("/{token}")
    public Result<Boolean> deleteToken(@PathVariable("token") String token) {
        return Result.success(tokenService.removeToken(token));
    }

    /**
     * 查询token列表
     *
     * @param page
     * @param username
     * @return
     */
    @GetMapping("page")
    public Result<Page> tokenList(Page page, String username) {
        if (StringUtils.isNotBlank(username)) {
            return Result.success(tokenService.queryTokenByUsername(page, username));
        }
        return Result.success(tokenService.queryToken(page));
    }

    /**
     * 获取token信息
     *
     * @param token
     * @return
     */
    @GetMapping("query-token")
    public Result<OAuth2AccessToken> queryToken(String token) {
        return Result.success(tokenService.queryTokenInfo(token));
    }
}
