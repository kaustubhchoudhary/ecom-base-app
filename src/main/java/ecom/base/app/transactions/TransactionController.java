package ecom.base.app.transactions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecom.base.app.response.dtos.ApiResponseDTO;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponseDTO<TransactionResponseDTO>> getTransactionById(
            @PathVariable Long transactionId) {

        TransactionResponseDTO responseDTO = transactionService.getTransactionById(transactionId);

        ApiResponseDTO<TransactionResponseDTO> response = new ApiResponseDTO<>();
        response.setMessage("Transaction fetched successfully");
        response.setData(responseDTO);

        return ResponseEntity.ok(response);
    }
}