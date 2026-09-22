package ecom.base.app.orderitems;

import lombok.Data;

@Data
public class OrderItemRequestDTO {

    private Long productId;

    private Integer quantity;
}