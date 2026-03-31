package com.hotel.dto;

/**
 * PaymentRequest - DTO for processing a mock payment
 */
public class PaymentRequest {

    // CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING
    private String paymentMethod;
    private String cardLastFour;
    private String cardHolderName;

    public PaymentRequest() {}

    public PaymentRequest(String paymentMethod, String cardLastFour, String cardHolderName) {
        this.paymentMethod = paymentMethod;
        this.cardLastFour = cardLastFour;
        this.cardHolderName = cardHolderName;
    }

    public String getPaymentMethod() { return paymentMethod; }
    public String getCardLastFour() { return cardLastFour; }
    public String getCardHolderName() { return cardHolderName; }

    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setCardLastFour(String cardLastFour) { this.cardLastFour = cardLastFour; }
    public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }
}
