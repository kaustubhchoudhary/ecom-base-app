package ecom.base.app.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecom.base.app.orders.OrderResponseDTO;
import ecom.base.app.orders.OrderService;
import ecom.base.app.payments.PaymentResponseDTO;
import ecom.base.app.payments.PaymentService;
import ecom.base.app.response.dtos.ApiResponseDTO;
import ecom.base.app.transactions.TransactionResponseDTO;
import ecom.base.app.transactions.TransactionService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

        private final UserService userService;
        private final OrderService orderService;
        private final PaymentService paymentService;
        private final TransactionService transactionService;

        public UserController(UserService userService, OrderService orderService, PaymentService paymentService,
                        TransactionService transactionService) {
                this.userService = userService;
                this.orderService = orderService;
                this.paymentService = paymentService;
                this.transactionService = transactionService;
        }

        @GetMapping
        public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getAllUsers() {

                return ResponseEntity.ok(
                                new ApiResponseDTO<List<UserResponseDTO>>(
                                                "Users fetched successfully",
                                                userService.getAllUsers()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserById(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                new ApiResponseDTO<UserResponseDTO>(
                                                "User fetched successfully",
                                                userService.getUserById(id)));
        }

        @PostMapping
        public ResponseEntity<ApiResponseDTO<UserResponseDTO>> addUser(
                        @RequestBody UserRequestDTO userRequestDTO) {

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(
                                                new ApiResponseDTO<UserResponseDTO>(
                                                                "User added successfully",
                                                                userService.addUser(userRequestDTO)));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUser(
                        @PathVariable Long id,
                        @RequestBody UserRequestDTO userRequestDTO) {

                return ResponseEntity.ok(
                                new ApiResponseDTO<UserResponseDTO>(
                                                "User updated successfully",
                                                userService.updateUser(id, userRequestDTO)));
        }

        @PatchMapping("/{id}")
        public ResponseEntity<ApiResponseDTO<UserResponseDTO>> toggleUserStatus(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                new ApiResponseDTO<UserResponseDTO>(
                                                "User status toggled successfully",
                                                userService.toggleUserStatus(id)));
        }

        @GetMapping("/me")
        public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getCurrentUser(
                        @RequestParam Long id) {

                return ResponseEntity.ok(
                                new ApiResponseDTO<UserResponseDTO>(
                                                "Current user fetched successfully",
                                                userService.getCurrentUser(id)));
        }

        @PutMapping("/me")
        public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateCurrentUser(
                        @RequestParam Long id,
                        @RequestBody UserRequestDTO userRequestDTO) {

                return ResponseEntity.ok(
                                new ApiResponseDTO<UserResponseDTO>(
                                                "Current user updated successfully",
                                                userService.updateCurrentUser(id, userRequestDTO)));
        }

        @GetMapping("/{userId}/orders")
        public ResponseEntity<ApiResponseDTO<List<OrderResponseDTO>>> getUserOrders(
                        @PathVariable Long userId) {

                List<OrderResponseDTO> orders = orderService.getOrdersByUser(userId);

                ApiResponseDTO<List<OrderResponseDTO>> response = new ApiResponseDTO<>();

                response.setMessage("Customer orders fetched successfully");
                response.setData(orders);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/{userId}/payments")
        public ResponseEntity<ApiResponseDTO<List<PaymentResponseDTO>>> getUserPayments(
                        @PathVariable Long userId) {

                List<PaymentResponseDTO> responseDTO = paymentService.getPaymentsByUserId(userId);

                ApiResponseDTO<List<PaymentResponseDTO>> response = new ApiResponseDTO<>();

                response.setMessage("Payment history fetched successfully");
                response.setData(responseDTO);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/{userId}/transactions")
        public ResponseEntity<ApiResponseDTO<List<TransactionResponseDTO>>> getUserTransactions(
                        @PathVariable Long userId) {

                List<TransactionResponseDTO> responseDTO = transactionService.getTransactionsByUserId(userId);

                ApiResponseDTO<List<TransactionResponseDTO>> response = new ApiResponseDTO<>();

                response.setMessage("Transaction history fetched successfully");
                response.setData(responseDTO);

                return ResponseEntity.ok(response);
        }
}