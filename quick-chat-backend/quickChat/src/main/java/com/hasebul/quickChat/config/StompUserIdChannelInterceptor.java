package com.hasebul.quickChat.config;

import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.security.Principal;

/**
 * Binds a STOMP session to the application user id from the connect frame header {@code userId},
 * so {@code convertAndSendToUser(userId, ...)} delivers to the right subscription.
 */
public class StompUserIdChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) {
            return message;
        }
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userId = firstHeader(accessor, "userId", "user-id");
            if (userId != null && !userId.isEmpty()) {
                String name = userId;
                accessor.setUser((Principal) () -> name);
            }
        }
        return message;
    }

    private static String firstHeader(StompHeaderAccessor accessor, String... names) {
        for (String n : names) {
            String v = accessor.getFirstNativeHeader(n);
            if (v != null && !v.isEmpty()) {
                return v;
            }
        }
        return null;
    }
}
