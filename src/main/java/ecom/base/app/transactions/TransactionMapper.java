package ecom.base.app.transactions;

public class TransactionMapper {

    public static TransactionResponseDTO toTransactionResponseDTO(
            Transaction transaction) {

        TransactionResponseDTO responseDTO = new TransactionResponseDTO();

        responseDTO.setId(transaction.getId());
        responseDTO.setPaymentId(transaction.getPayment().getId());
        responseDTO.setTransactionRef(transaction.getTransactionRef());
        responseDTO.setAmount(transaction.getAmount());
        responseDTO.setType(transaction.getType());
        responseDTO.setStatus(transaction.getStatus());
        responseDTO.setTransactionDate(transaction.getTransactionDate());
        responseDTO.setCreatedAt(transaction.getCreatedAt());

        return responseDTO;
    }
}