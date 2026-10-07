package ecom.base.app.payments;

public class PaymentMapper {

    public static PaymentResponseDTO toPaymentResponseDTO(
            Payment payment) {

        PaymentResponseDTO responseDTO = new PaymentResponseDTO();

        responseDTO.setId(payment.getId());
        responseDTO.setOrderId(payment.getOrder().getId());
        responseDTO.setAmount(payment.getAmount());
        responseDTO.setPaymentMethod(payment.getPaymentMethod());
        responseDTO.setStatus(payment.getStatus());
        responseDTO.setGateway(payment.getGateway());
        responseDTO.setCreatedAt(payment.getCreatedAt());
        responseDTO.setUpdatedAt(payment.getUpdatedAt());

        return responseDTO;
    }
}