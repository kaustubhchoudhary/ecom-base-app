package ecom.base.app.payments;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecom.base.app.exceptions.ResourceAlreadyExistsException;
import ecom.base.app.orders.Order;
import ecom.base.app.orders.OrderRepository;
import ecom.base.app.user.User;
import ecom.base.app.user.UserRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PaymentResponseDTO createPayment(
            Long orderId,
            PaymentRequestDTO requestDTO) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (paymentRepository.existsByOrderId(orderId)) {
            throw new ResourceAlreadyExistsException(
                    "Payment already exists for this order");
        }

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(requestDTO.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentMapper.toPaymentResponseDTO(savedPayment);
    }

    public PaymentResponseDTO getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return PaymentMapper.toPaymentResponseDTO(payment);
    }

    public PaymentResponseDTO getPaymentByOrderId(Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException(
                        "Payment not found for this order"));

        return PaymentMapper.toPaymentResponseDTO(payment);
    }

    public List<PaymentResponseDTO> getPaymentsByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return paymentRepository.findByOrderUserId(user.getId())
                .stream()
                .map(PaymentMapper::toPaymentResponseDTO)
                .toList();
    }
}
