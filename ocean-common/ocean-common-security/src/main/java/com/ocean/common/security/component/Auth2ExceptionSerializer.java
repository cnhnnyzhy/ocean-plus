package com.ocean.common.security.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ocean.common.core.exception.GlobalErrorCode;
import com.ocean.common.security.exception.CustomAuth2Exception;
import lombok.SneakyThrows;

/**
 * Auth2异常序列化
 *
 * @author ocean
 * @date 2022/10/22
 */
public class Auth2ExceptionSerializer extends StdSerializer<CustomAuth2Exception> {
    public Auth2ExceptionSerializer() {
        super(CustomAuth2Exception.class);
    }

    @SneakyThrows
    @Override
    public void serialize(CustomAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("code", GlobalErrorCode.FAILED.getCode());
        jsonGenerator.writeObjectField("msg", e.getMessage());
        jsonGenerator.writeObjectField("data", e.getErrorCode());
        jsonGenerator.writeEndObject();
    }
}
