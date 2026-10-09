package ecom.base.app.transactions;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecom.base.app.payments.Payment;
import ecom.base.app.payments.PaymentRepository;
import ecom.base.app.user.UserRepository;
import ecom.base.app.orders.OrderRepository;

@Service
public class TransactionService {

        private final TransactionRepository transactionRepository;
        private final PaymentRepository paymentRepository;
        private final UserRepository userRepository;
        private final OrderRepository orderRepository;

        public TransactionService(
                        TransactionRepository transactionRepository,
                        PaymentRepository paymentRepository,
                        UserRepository userRepository,
                        OrderRepository orderRepository) {

                this.transactionRepository = transactionRepository;
                this.paymentRepository = paymentRepository;
                this.userRepository = userRepository;
                this.orderRepository = orderRepository;
        }

        @Transactional
        public TransactionResponseDTO createTransaction(
                        Long paymentId,
                        TransactionRequestDTO requestDTO) {

                Payment payment = paymentRepository.findById(paymentId)
                                .orElseThrow(() -> new RuntimeException("Payment not found"));

                Transaction transaction = new Transaction();

                transaction.setPayment(payment);
                transaction.setTransactionRef("TXN-" + System.currentTimeMillis());
                transaction.setAmount(requestDTO.getAmount());
                transaction.setType(requestDTO.getType());
                transaction.setStatus("SUCCESS");
                transaction.setTransactionDate(LocalDateTime.now());

                Transaction savedTransaction = transactionRepository.save(transaction);

                return TransactionMapper.toTransactionResponseDTO(savedTransaction);
        }

        public TransactionResponseDTO getTransactionById(Long transactionId) {

                Transaction transaction = transactionRepository.findById(transactionId)
                                .orElseThrow(() -> new RuntimeException("Transaction not found"));

                return TransactionMapper.toTransactionResponseDTO(transaction);
        }

        public List<TransactionResponseDTO> getTransactionsByPaymentId(
                        Long paymentId) {

                paymentRepository.findById(paymentId)
                                .orElseThrow(() -> new RuntimeException("Payment not found"));

                return transactionRepository.findByPaymentId(paymentId)
                                .stream()
                                .map(TransactionMapper::toTransactionResponseDTO)
                                .toList();
        }

        public List<TransactionResponseDTO> getTransactionsByUserId(
                        Long userId) {

                userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                return transactionRepository.findByPaymentOrderUserId(userId)
                                .stream()
                                .map(TransactionMapper::toTransactionResponseDTO)
                                .toList();
        }

        public List<TransactionResponseDTO> getTransactionsByOrderId(
                        Long orderId) {

                orderRepository.findById(orderId)
                                .orElseThrow(() -> new RuntimeException("Order not found"));

                return transactionRepository.findByPaymentOrderId(orderId)
                                .stream()
                                .map(TransactionMapper::toTransactionResponseDTO)
                                .toList();
        }
}