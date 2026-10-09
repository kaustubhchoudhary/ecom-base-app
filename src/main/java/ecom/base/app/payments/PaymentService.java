package ecom.base.app.payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecom.base.app.exceptions.RefundAmountExceededException;
import ecom.base.app.exceptions.ResourceAlreadyExistsException;
import ecom.base.app.exceptions.ResourceNotFoundException;
import ecom.base.app.orders.Order;
import ecom.base.app.orders.OrderRepository;
import ecom.base.app.transactions.Transaction;
import ecom.base.app.transactions.TransactionMapper;
import ecom.base.app.transactions.TransactionRepository;
import ecom.base.app.transactions.TransactionResponseDTO;
import ecom.base.app.transactions.TransactionType;
import ecom.base.app.user.User;
import ecom.base.app.user.UserRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            UserRepository userRepository, TransactionRepository transactionRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public PaymentResponseDTO createPayment(
            Long orderId,
            PaymentRequestDTO requestDTO) {

        // 1. Find order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // 2. Check payment doesn't already exist
        if (paymentRepository.existsByOrderId(orderId)) {
            throw new ResourceAlreadyExistsException(
                    "Payment already exists for this order");
        }

        // 3. Create Payment
        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(requestDTO.getPaymentMethod());

        // 4. Save Payment
        Payment savedPayment = paymentRepository.save(payment);

        // 5. Create PAYMENT Transaction
        Transaction transaction = new Transaction();
        transaction.setPayment(savedPayment);
        transaction.setTransactionRef("TXN-" + System.currentTimeMillis());
        transaction.setAmount(savedPayment.getAmount());
        transaction.setType(TransactionType.PAYMENT);
        transaction.setStatus("SUCCESS");
        transaction.setTransactionDate(LocalDateTime.now());

        // 6. Save Transaction
        transactionRepository.save(transaction);

        // 7. Return Payment response

        return PaymentMapper.toPaymentResponseDTO(savedPayment);
    }

    @Transactional
    public RefundResponseDTO refundPayment(
            Long paymentId,
            RefundRequestDTO requestDTO) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        BigDecimal refundAmount = requestDTO.getAmount();

        if (refundAmount == null ||
                refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Refund amount must be greater than zero");
        }

        List<Transaction> transactions = transactionRepository.findByPaymentId(paymentId);

        BigDecimal totalRefunded = transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.REFUND
                        && "SUCCESS".equals(transaction.getStatus()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remainingBeforeRefund = payment.getAmount().subtract(totalRefunded);

        if (refundAmount.compareTo(remainingBeforeRefund) > 0) {
            throw new RefundAmountExceededException(
                    "Refund amount cannot exceed the remaining refundable amount");
        }

        Transaction transaction = new Transaction();
        transaction.setPayment(payment);
        transaction.setTransactionRef("REF-" + System.currentTimeMillis());
        transaction.setAmount(refundAmount);
        transaction.setType(TransactionType.REFUND);
        transaction.setStatus("SUCCESS");
        transaction.setTransactionDate(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);

        BigDecimal remainingRefundableAmount = remainingBeforeRefund.subtract(refundAmount);

        RefundResponseDTO responseDTO = new RefundResponseDTO();
        responseDTO.setTransactionId(savedTransaction.getId());
        responseDTO.setPaymentId(paymentId);
        responseDTO.setAmount(refundAmount);
        responseDTO.setType(savedTransaction.getType());
        responseDTO.setStatus(savedTransaction.getStatus());
        responseDTO.setRemainingRefundableAmount(remainingRefundableAmount);

        return responseDTO;
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
