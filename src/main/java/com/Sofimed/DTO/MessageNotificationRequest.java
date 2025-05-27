package com.Sofimed.DTO;

//DTO pour la requête
public  class MessageNotificationRequest {
  private Long recipientId;
  private String message;
  private String link;

  // getters et setters
  public Long getRecipientId() { return recipientId; }
  public void setRecipientId(Long recipientId) { this.recipientId = recipientId; }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
  public String getLink() { return link; }
  public void setLink(String link) { this.link = link; }
}