package org.example.backend.model;

import java.util.List;

import com.paypal.sdk.models.CheckoutPaymentIntent;
import com.paypal.sdk.models.LinkDescription;
import com.paypal.sdk.models.OrderStatus;
import com.paypal.sdk.models.Payer;
import com.paypal.sdk.models.PaymentSourceResponse;
import com.paypal.sdk.models.PurchaseUnit;


public record Order(
    String createTime,
    String updateTime,
    String id,
    PaymentSourceResponse paymentSource,
    CheckoutPaymentIntent intent,
    Payer payer,
    List<PurchaseUnit> purchaseUnits,
    OrderStatus status,
    List<LinkDescription> links
) {}
