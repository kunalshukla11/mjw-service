package com.mjw.mjwservice.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Configuration
@SuppressWarnings("checkstyle:finalparameters")
//TODO-Fix this later having good strategy for rounding of BigDecimal
public class BigDecimalConfig {

    private static final int PRECISION = 10; // Total digits
    private static final int SCALE = 0; // Decimal places

    @Bean
    public SimpleModule bigDecimalModule() {
        final SimpleModule module = new SimpleModule();

        // Serializer
        module.addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
            @Override
            public void serialize(BigDecimal value, final JsonGenerator gen,
                                  final SerializerProvider serializers) throws IOException {
                if (value != null) {
                    value = value.setScale(SCALE, RoundingMode.HALF_UP);
                }
                gen.writeNumber(value);
            }
        });

        // Deserializer
        module.addDeserializer(BigDecimal.class, new JsonDeserializer<BigDecimal>() {
            @Override
            public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                BigDecimal value = p.getDecimalValue();
                if (value != null) {
                    value = value.setScale(SCALE, RoundingMode.HALF_UP);
                }
                return value;
            }
        });

        return module;
    }
}