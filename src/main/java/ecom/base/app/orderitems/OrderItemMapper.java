package ecom.base.app.orderitems;

import java.math.BigDecimal;

import ecom.base.app.orders.Order;
import ecom.base.app.product.Product;

public class OrderItemMapper {

    public static OrderItem toOrderItem(
            OrderItemRequestDTO requestDTO,
            Order order,
            Product product) {

        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(requestDTO.getQuantity());
        orderItem.setUnitPrice(product.getPrice());

        return orderItem;
    }

    public static OrderItemResponseDTO toOrderItemResponseDTO(
            OrderItem orderItem) {

        OrderItemResponseDTO responseDTO = new OrderItemResponseDTO();

        responseDTO.setId(orderItem.getId());
        responseDTO.setProductId(orderItem.getProduct().getId());
        responseDTO.setProductName(orderItem.getProduct().getName());
        responseDTO.setQuantity(orderItem.getQuantity());
        responseDTO.setUnitPrice(orderItem.getUnitPrice());

        responseDTO.setTotalPrice(
                orderItem.getUnitPrice()
                        .multiply(
                                BigDecimal.valueOf(orderItem.getQuantity())));

        return responseDTO;
    }
}