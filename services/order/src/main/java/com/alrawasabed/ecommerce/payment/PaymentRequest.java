package com.alrawasabed.ecommerce.payment;



import com.alrawasabed.ecommerce.order.CustomerResponse;
import com.alrawasabed.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}