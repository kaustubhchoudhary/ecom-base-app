package ecom.base.app.transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionResponseDTO {

    private Long id;

    private Long paymentId;

    private String transactionRef;

    private BigDecimal amount;

    private TransactionType type;

    private String gateway;

    private String status;

    private String responseCode;

    private String responseMessage;

    private LocalDateTime transactionDate;

    private LocalDateTime createdAt;
}