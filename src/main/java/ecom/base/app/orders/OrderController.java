package ecom.base.app.orders;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecom.base.app.response.dtos.ApiResponseDTO;
import ecom.base.app.transactions.TransactionResponseDTO;
import ecom.base.app.transactions.TransactionService;
import ecom.base.app.orderitems.OrderItemResponseDTO;
import ecom.base.app.payments.PaymentRequestDTO;
import ecom.base.app.payments.PaymentResponseDTO;
import ecom.base.app.payments.PaymentService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final TransactionService transactionService;

    public OrderController(OrderService orderService, PaymentService paymentService,
            TransactionService transactionService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> createOrder(
            @RequestBody OrderRequestDTO orderRequestDTO) {

        OrderResponseDTO responseDTO = orderService.createOrder(orderRequestDTO);

        ApiResponseDTO<OrderResponseDTO> response = new ApiResponseDTO<>();

        response.setMessage("Order created successfully");
        response.setData(responseDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> getOrderById(
            @PathVariable Long orderId) {

        OrderResponseDTO responseDTO = orderService.getOrderById(orderId);

        ApiResponseDTO<OrderResponseDTO> response = new ApiResponseDTO<>();

        response.setMessage("Order fetched successfully");
        response.setData(responseDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<ApiResponseDTO<List<OrderItemResponseDTO>>> getOrderItems(
            @PathVariable Long orderId) {

        List<OrderItemResponseDTO> responseDTO = orderService.getOrderItems(orderId);

        ApiResponseDTO<List<OrderItemResponseDTO>> response = new ApiResponseDTO<>();

        response.setMessage("Order items fetched successfully");
        response.setData(responseDTO);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusRequestDTO requestDTO) {

        OrderResponseDTO responseDTO = orderService.changeOrderStatus(
                orderId,
                requestDTO.getStatus());

        ApiResponseDTO<OrderResponseDTO> response = new ApiResponseDTO<>();

        response.setMessage("Order status updated successfully");
        response.setData(responseDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>> createPayment(
            @PathVariable Long orderId,
            @RequestBody PaymentRequestDTO requestDTO) {

        PaymentResponseDTO responseDTO = paymentService.createPayment(orderId, requestDTO);

        ApiResponseDTO<PaymentResponseDTO> response = new ApiResponseDTO<>();
        response.setMessage("Payment created successfully");
        response.setData(responseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}/payment")
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>> getOrderPayment(
            @PathVariable Long orderId) {

        PaymentResponseDTO responseDTO = paymentService.getPaymentByOrderId(orderId);

        ApiResponseDTO<PaymentResponseDTO> response = new ApiResponseDTO<>();
        response.setMessage("Payment fetched successfully");
        response.setData(responseDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}/transactions")
    public ResponseEntity<ApiResponseDTO<List<TransactionResponseDTO>>> getOrderTransactions(
            @PathVariable Long orderId) {

        List<TransactionResponseDTO> responseDTO = transactionService.getTransactionsByOrderId(orderId);

        ApiResponseDTO<List<TransactionResponseDTO>> response = new ApiResponseDTO<>();

        response.setMessage("Order transactions fetched successfully");
        response.setData(responseDTO);

        return ResponseEntity.ok(response);
    }
}