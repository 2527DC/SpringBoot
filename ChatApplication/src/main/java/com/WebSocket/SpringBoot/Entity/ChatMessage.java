package com.WebSocket.SpringBoot.Entity;


public class ChatMessage {
    private String content; // The message content
    private String recipient; // The recipient's username
    private String sender; // The sender's username (can be optional if derived from session)

     private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    
    @Override
    public String toString() {
        return "ChatMessage{" +
                "content='" + content + '\'' +
                ", recipient='" + recipient + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
