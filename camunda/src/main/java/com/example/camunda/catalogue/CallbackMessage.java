package com.example.camunda.catalogue;

public enum CallbackMessage {
    MESSAGE_CONTINUE_A("MessageContinueA"),
    MESSAGE_CONTINUE_B("MessageContinueB");

    public final String message;

    private CallbackMessage(String message) {
        this.message = message;
    }

    public static CallbackMessage valueOfMessage(String message) {
        for (CallbackMessage callbackMessage : values()) {
            if (callbackMessage.message.equals(message)) {
                return callbackMessage;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return message;
    }
}