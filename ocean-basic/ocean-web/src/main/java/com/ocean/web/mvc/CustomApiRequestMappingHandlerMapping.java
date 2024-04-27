package com.ocean.web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 自定义API RequestMappingHandlerMapping
 *
 * @author ocean
 * @date 2024/4/24
 */
@Slf4j
public class CustomApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info == null) {
            return info;
        }
        info = replacePatterns(handlerType, info);
        info = appendParentRequestMapping(handlerType.getSuperclass(), info);
        logMappingInfo(info);
        return info;
    }

    private RequestMappingInfo replacePatterns(Class<?> handlerType, RequestMappingInfo info) {
        PatternsRequestCondition patternsCondition = info.getPatternsCondition();
        if (patternsCondition == null) {
            return info;
        }
        RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
        if (typeInfo == null) {
            return info;
        }
        PatternsRequestCondition typeInfoPatternsCondition = typeInfo.getPatternsCondition();
        String typeInfoPattern = typeInfoPatternsCondition != null ?
                CollectionUtils.isNotEmpty(typeInfoPatternsCondition.getPatterns()) ? typeInfoPatternsCondition.getPatterns().iterator().next() : null
                :
                null;
        if (StringUtils.isBlank(typeInfoPattern)) {
            return info;
        }
        new ArrayList<>(patternsCondition.getPatterns()).forEach(pattern -> {
            patternsCondition.getPatterns().remove(pattern);
            patternsCondition.getPatterns().add(pattern.replaceFirst(typeInfoPattern, StringUtils.EMPTY));
        });
        return info;
    }

    @Nullable
    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = (element instanceof Class ?
                getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
        return (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);
    }

    /**
     * 添加父类的mapping
     *
     * @param handlerType
     * @param mappingInfo
     * @return
     */
    private RequestMappingInfo appendParentRequestMapping(Class<?> handlerType, RequestMappingInfo mappingInfo) {
        if (handlerType == null) {
            return mappingInfo;
        }
        RequestMapping parentRequestMapping = handlerType.getAnnotation(RequestMapping.class);
        if (parentRequestMapping != null && parentRequestMapping.value().length > 0) {
            //使用path工具向前追加父类的path
            mappingInfo = RequestMappingInfo.paths(parentRequestMapping.value()).build().combine(mappingInfo);
        }
        return appendParentRequestMapping(handlerType.getSuperclass(), mappingInfo);
    }

    /**
     * 由于spring boot2不打印mapping了，不习惯，就自己打印一下，但是有些系统mapping也不打印，有空再研究怎么打印
     *
     * @param info
     */
    private void logMappingInfo(RequestMappingInfo info) {
        if (info == null) {
            return;
        }
        log.info("mapping path: {}", info.toString());
    }
}
