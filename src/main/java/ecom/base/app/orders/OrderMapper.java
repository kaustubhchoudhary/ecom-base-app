package ecom.base.app.orders;

import ecom.base.app.user.User;

public class OrderMapper {

    public static Order toOrder(OrderRequestDTO requestDTO, User user) {

        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);

        return order;
    }

    public static OrderResponseDTO toOrderResponseDTO(Order order) {

        OrderResponseDTO responseDTO = new OrderResponseDTO();

        responseDTO.setId(order.getId());
        responseDTO.setUserId(order.getUser().getId());
        responseDTO.setTotalAmount(order.getTotalAmount());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setCreatedAt(order.getCreatedAt());
        responseDTO.setUpdatedAt(order.getUpdatedAt());

        return responseDTO;
    }
}