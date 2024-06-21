package com.alrawasabed.ecommerce.order;

import com.alrawasabed.ecommerce.orderline.OrderLineService;
import com.alrawasabed.ecommerce.product.ProductClient;
import com.alrawasabed.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    public Integer create(OrderRequest request) {

        // check the customer --> OpenFeign
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided id: " + request.customerId()));

        // purchase the product --> product-ms
        productClient.purchaseProducts(request.products());

        // persist order
        var order = orderRepository.save(mapper.toOrder(request));

        // persist order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // start payment process

        // send the order confirmation --> notification-ms (kafka)

        return null;
    }
}
