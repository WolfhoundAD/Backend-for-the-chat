package dev.vorstu.dto;

import dev.vorstu.enums.OrderStatus;
import dev.vorstu.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDTO {
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private Long customerId;
}
