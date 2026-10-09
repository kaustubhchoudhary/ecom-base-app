package ecom.base.app.payments;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecom.base.app.response.dtos.ApiResponseDTO;
import ecom.base.app.transactions.TransactionRequestDTO;
import ecom.base.app.transactions.TransactionResponseDTO;
import ecom.base.app.transactions.TransactionService;

@RestController
@RequestMapping("/api/v1/payments/{paymentId}")
public class PaymentController {

    private final PaymentService paymentService;
    private final TransactionService transactionService;

    public PaymentController(PaymentService paymentService, TransactionService transactionService) {
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>> getPaymentById(
            @PathVariable Long paymentId) {

        PaymentResponseDTO responseDTO = paymentService.getPaymentById(paymentId);

        ApiResponseDTO<PaymentResponseDTO> response = new ApiResponseDTO<>();

        response.setMessage("Payment fetched successfully");
        response.setData(responseDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund")
    public ResponseEntity<ApiResponseDTO<RefundResponseDTO>> refundPayment(
            @PathVariable Long paymentId,
            @RequestBody RefundRequestDTO requestDTO) {

        RefundResponseDTO responseDTO = paymentService.refundPayment(paymentId, requestDTO);

        ApiResponseDTO<RefundResponseDTO> response = new ApiResponseDTO<>();
        response.setMessage("Refund processed successfully");
        response.setData(responseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/transactions")
    public ResponseEntity<ApiResponseDTO<TransactionResponseDTO>> createTransaction(
            @PathVariable Long paymentId,
            @RequestBody TransactionRequestDTO requestDTO) {

        TransactionResponseDTO responseDTO = transactionService.createTransaction(paymentId, requestDTO);

        ApiResponseDTO<TransactionResponseDTO> response = new ApiResponseDTO<>();
        response.setMessage("Transaction created successfully");
        response.setData(responseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/transactions")
    public ResponseEntity<ApiResponseDTO<List<TransactionResponseDTO>>> getPaymentTransactions(
            @PathVariable Long paymentId) {

        List<TransactionResponseDTO> responseDTO = transactionService.getTransactionsByPaymentId(paymentId);

        ApiResponseDTO<List<TransactionResponseDTO>> response = new ApiResponseDTO<>();

        response.setMessage("Payment transactions fetched successfully");
        response.setData(responseDTO);

        return ResponseEntity.ok(response);
    }

}