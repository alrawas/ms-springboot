package com.alrawasabed.ecommerce.order;

public record OrderLineRequest(Integer id,
                               Integer orderId,
                               Integer productId,
                               double quantity) {
}
