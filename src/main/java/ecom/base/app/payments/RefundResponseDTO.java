package ecom.base.app.payments;

import java.math.BigDecimal;

import ecom.base.app.transactions.TransactionType;
import lombok.Data;

@Data
public class RefundResponseDTO {

    private Long transactionId;
    private Long paymentId;
    private BigDecimal amount;
    private TransactionType type;
    private String status;
    private BigDecimal remainingRefundableAmount;
}