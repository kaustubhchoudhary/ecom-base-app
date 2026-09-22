package ecom.base.app.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import ecom.base.app.orderitems.OrderItemResponseDTO;
import lombok.Data;

@Data
public class OrderResponseDTO {

    private Long id;

    private Long userId;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private List<OrderItemResponseDTO> items;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}