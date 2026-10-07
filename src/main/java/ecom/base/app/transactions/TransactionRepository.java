package ecom.base.app.transactions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByPaymentId(Long paymentId);

    List<Transaction> findByPaymentOrderUserId(Long userId);

    List<Transaction> findByPaymentOrderId(Long orderId);
}