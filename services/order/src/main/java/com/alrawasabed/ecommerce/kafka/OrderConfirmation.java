package com.alrawasabed.ecommerce.kafka;

import com.alrawasabed.ecommerce.order.CustomerResponse;
import com.alrawasabed.ecommerce.order.PaymentMethod;
import com.alrawasabed.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
