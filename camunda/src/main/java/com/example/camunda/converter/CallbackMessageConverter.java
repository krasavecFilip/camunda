package com.example.camunda.converter;

import com.example.camunda.catalogue.CallbackMessage;
import org.springframework.core.convert.converter.Converter;

public class CallbackMessageConverter implements Converter<String, CallbackMessage> {
    @Override
    public CallbackMessage convert(String message) {
        return CallbackMessage.valueOfMessage(message);
    }
}