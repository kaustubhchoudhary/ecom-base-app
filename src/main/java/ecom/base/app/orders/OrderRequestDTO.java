package ecom.base.app.orders;

import java.util.List;

import ecom.base.app.orderitems.OrderItemRequestDTO;
import lombok.Data;

@Data
public class OrderRequestDTO {

    private Long userId;

    private List<OrderItemRequestDTO> items;
}
