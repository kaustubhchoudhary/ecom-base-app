package ecom.base.app.transactions;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionRequestDTO {

    private BigDecimal amount;

    private TransactionType type;
}