package com.example.javaspringbootnavy1.modal;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class PaymentDetails {
    private String paymentId;

    private String vnpayTransactionId;
    private String vnpayOrderInfo;
    private String vnpayResponseCode;
    private PaymentStatus status;

}
