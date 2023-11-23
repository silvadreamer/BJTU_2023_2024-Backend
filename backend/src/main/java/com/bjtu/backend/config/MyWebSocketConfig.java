//package com.bjtu.backend.config;
//
//import com.bjtu.backend.service.impl.Code.WebSocketClientHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@EnableWebSocket
//public class MyWebSocketConfig implements WebSocketConfigurer
//{
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
//    {
//        registry.addHandler(webSocketClientHandler(), "/websocket").setAllowedOrigins("*");
//    }
//
//    @Bean
//    public WebSocketClientHandler webSocketClientHandler()
//    {
//        return new WebSocketClientHandler();
//    }
//}
