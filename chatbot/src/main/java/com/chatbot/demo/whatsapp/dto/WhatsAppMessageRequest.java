package com.chatbot.demo.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WhatsAppMessageRequest {

    @JsonProperty("messaging_product")
    private String messagingProduct = "whatsapp";

    @JsonProperty("recipient_type")
    private String recipientType = "individual";

    private String to;
    private String type = "text";
    private TextContent text;

    public WhatsAppMessageRequest() {
    }

    public WhatsAppMessageRequest(String to, String message) {
        this.to = to;
        this.text = new TextContent(message);
    }

    public String getMessagingProduct() {
        return messagingProduct;
    }

    public void setMessagingProduct(String messagingProduct) {
        this.messagingProduct = messagingProduct;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TextContent getText() {
        return text;
    }

    public void setText(TextContent text) {
        this.text = text;
    }

    public static class TextContent {
        @JsonProperty("preview_url")
        private boolean previewUrl = false;
        private String body;

        public TextContent() {
        }

        public TextContent(String body) {
            this.body = body;
        }

        public boolean isPreviewUrl() {
            return previewUrl;
        }

        public void setPreviewUrl(boolean previewUrl) {
            this.previewUrl = previewUrl;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}

