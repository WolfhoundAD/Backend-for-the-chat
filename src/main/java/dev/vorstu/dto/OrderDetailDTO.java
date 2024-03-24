package dev.vorstu.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDetailDTO {
    private Long orderId;
    private Long productId;
    private BigDecimal price;
    private int quantity;
}
