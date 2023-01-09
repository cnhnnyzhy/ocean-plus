package com.ocean.gateway.handler;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocean.common.core.constant.Constants;
import com.ocean.common.core.dto.Result;
import com.ocean.common.core.util.SpringContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 图像验证码校验处理器
 *
 * @author ocean
 * @date 2023/1/9
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCodeCheckHandler implements HandlerFunction<ServerResponse> {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        CaptchaVO vo = new CaptchaVO();
        vo.setPointJson(request.queryParam("pointJson").get());
        vo.setToken(request.queryParam("token").get());
        vo.setCaptchaType(Constants.IMAGE_CODE_TYPE);

        CaptchaService captchaService = SpringContextHolder.getBean(CaptchaService.class);
        ResponseModel responseModel = captchaService.check(vo);

        return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(Result.success(responseModel))));
    }
}