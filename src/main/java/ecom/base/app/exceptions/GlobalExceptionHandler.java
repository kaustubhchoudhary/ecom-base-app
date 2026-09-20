package ecom.base.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ecom.base.app.response.dtos.ApiResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(exception = ResourceNotFoundException.class)
        public ResponseEntity<ApiResponseDTO<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {

                System.out.println("GlobalExceptionHandler : handleResourceNotFoundException");

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponseDTO<Object>(
                                                ex.getMessage(),
                                                null));

        }

        @ExceptionHandler(exception = DuplicateResourceException.class)
        public ResponseEntity<ApiResponseDTO<Object>> handleDuplicateCategoryException(
                        DuplicateResourceException exception) {

                ApiResponseDTO<Object> responseDTO = new ApiResponseDTO<Object>(exception.getMessage(), null);

                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);

        }

        @ExceptionHandler(exception = EntityAlreadyInactiveException.class)
        public ResponseEntity<ApiResponseDTO<Object>> handleCategoryAlreadyInactiveException(
                        EntityAlreadyInactiveException exception) {

                ApiResponseDTO<Object> responseDTO = new ApiResponseDTO<Object>(exception.getMessage(), null);

                return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        }

}
