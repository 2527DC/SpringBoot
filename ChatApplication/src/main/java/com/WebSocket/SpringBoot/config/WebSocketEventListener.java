    package com.WebSocket.SpringBoot.config;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.event.EventListener;
    import org.springframework.messaging.Message;
    import org.springframework.messaging.MessageHeaders;
    import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
    import org.springframework.messaging.simp.SimpMessagingTemplate;
    import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
    import org.springframework.stereotype.Component;
    import org.springframework.web.socket.messaging.SessionConnectEvent;
    import org.springframework.web.socket.messaging.SessionConnectedEvent;
    import org.springframework.web.socket.messaging.SessionDisconnectEvent;

    import java.util.List;


    @Component
    public class WebSocketEventListener {


        @Autowired
        private SimpMessagingTemplate simpMessagingTemplate;

        @Autowired
        private UserTrackingService userTrackingService;


        private final CustomChannelInterceptor customChannelInterceptor;

        public WebSocketEventListener(CustomChannelInterceptor customChannelInterceptor) {
            this.customChannelInterceptor = customChannelInterceptor;
        }

        @EventListener
        public void handleWebSocketConnectListener(SessionConnectEvent event) {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

            // Print the headers for debugging
            System.out.println("This is the header in SessionConnectEvent: " + headerAccessor);

            // Access the native headers to get the username
            List<String> usernames = headerAccessor.getNativeHeader("username");

            if (usernames != null && !usernames.isEmpty()) {
                String username = usernames.get(0); // Get the first username from the list
                System.out.println("Username from SessionConnectEvent: " + username);
            } else {
                System.out.println("Username header not found in SessionConnectEvent");
            }


        }
        @EventListener
        public void handleWebSocketDisconnectListener(SessionDisconnectEvent event ) {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());


            String username = (String) headerAccessor.getSessionAttributes().get("username");

            System.out.println(headerAccessor.getMessageHeaders() + " this is in event ");


            System.out.println(username);
            if (username != null) {
               userTrackingService.removeUser(username);
                System.out.println("User Disconnected: " + username);

                // Broadcast the updated list of online users to everyone
                simpMessagingTemplate.convertAndSend("/topic/onlineUsers",userTrackingService.getOnlineUsers());
            }
        }


    }