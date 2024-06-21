package com.alrawasabed.ecommerce.order;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
