package ecom.base.app.payments;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RefundRequestDTO {

    private BigDecimal amount;
}