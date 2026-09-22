package ecom.base.app.orders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecom.base.app.orderitems.*;
import ecom.base.app.product.Product;
import ecom.base.app.product.ProductRepository;
import ecom.base.app.user.User;
import ecom.base.app.user.UserRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal totalAmount = BigDecimal.ZERO;

        List<Product> products = new ArrayList<>();

        for (OrderItemRequestDTO itemRequest : requestDTO.getItems()) {

            Product product = productRepository.findById(
                    itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal itemTotal = product.getPrice()
                    .multiply(
                            BigDecimal.valueOf(itemRequest.getQuantity()));

            totalAmount = totalAmount.add(itemTotal);

            products.add(product);
        }

        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        for (int i = 0; i < requestDTO.getItems().size(); i++) {

            OrderItemRequestDTO itemRequest = requestDTO.getItems().get(i);

            Product product = products.get(i);

            OrderItem orderItem = OrderItemMapper.toOrderItem(
                    itemRequest,
                    savedOrder,
                    product);

            orderItems.add(orderItem);
        }

        orderItemRepository.saveAll(orderItems);

        OrderResponseDTO responseDTO = OrderMapper.toOrderResponseDTO(savedOrder);

        responseDTO.setItems(
                orderItems.stream()
                        .map(OrderItemMapper::toOrderItemResponseDTO)
                        .toList());

        return responseDTO;

    }

    public List<OrderResponseDTO> getOrdersByUser(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .map(order -> {

                    OrderResponseDTO responseDTO = OrderMapper.toOrderResponseDTO(order);

                    responseDTO.setItems(
                            orderItemRepository.findByOrderId(order.getId())
                                    .stream()
                                    .map(OrderItemMapper::toOrderItemResponseDTO)
                                    .toList());

                    return responseDTO;
                })
                .toList();
    }

    public OrderResponseDTO getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderResponseDTO responseDTO = OrderMapper.toOrderResponseDTO(order);

        responseDTO.setItems(
                orderItemRepository.findByOrderId(orderId)
                        .stream()
                        .map(OrderItemMapper::toOrderItemResponseDTO)
                        .toList());

        return responseDTO;
    }

    public List<OrderItemResponseDTO> getOrderItems(Long orderId) {

        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }

        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(OrderItemMapper::toOrderItemResponseDTO)
                .toList();
    }

    @Transactional
    public OrderResponseDTO changeOrderStatus(
            Long orderId,
            OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        return OrderMapper.toOrderResponseDTO(order);
    }
}