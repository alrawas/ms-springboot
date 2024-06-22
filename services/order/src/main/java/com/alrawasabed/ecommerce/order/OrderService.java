package com.alrawasabed.ecommerce.order;

import com.alrawasabed.ecommerce.kafka.OrderConfirmation;
import com.alrawasabed.ecommerce.kafka.OrderProducer;
import com.alrawasabed.ecommerce.orderline.OrderLineService;
import com.alrawasabed.ecommerce.payment.PaymentClient;
import com.alrawasabed.ecommerce.payment.PaymentRequest;
import com.alrawasabed.ecommerce.product.ProductClient;
import com.alrawasabed.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;
    public Integer create(OrderRequest request) {

        // check the customer --> OpenFeign
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided id: " + request.customerId()));

        // purchase the product --> product-ms
        var purchasedProducts = productClient.purchaseProducts(request.products());

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

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        // start payment process
        paymentClient.requestOrderPayment(paymentRequest);

        // send the order confirmation --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProducts
        ));

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream().map(mapper::fromOrder).toList();
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));
    }
}
