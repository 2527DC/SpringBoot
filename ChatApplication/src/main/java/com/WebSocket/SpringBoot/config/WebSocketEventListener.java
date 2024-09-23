    package com.WebSocket.SpringBoot.config;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.event.EventListener;
    import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
    import org.springframework.messaging.simp.SimpMessagingTemplate;
    import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
    import org.springframework.stereotype.Component;
    import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
    import org.springframework.web.socket.messaging.SessionDisconnectEvent;


    @Component
    public class WebSocketEventListener {


        @Autowired
        private SimpMessagingTemplate simpMessagingTemplate;

        @Autowired
        private UserTrackingService userTrackingService;

        @EventListener
        public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
            String username = (String) headerAccessor.getSessionAttributes().get("username");

            System.out.println(username);
            if (username != null) {
               userTrackingService.removeUser(username);
                System.out.println("User Disconnected: " + username);

                // Broadcast the updated list of online users to everyone
                simpMessagingTemplate.convertAndSend("/topic/onlineUsers",userTrackingService);
            }
        }


    }