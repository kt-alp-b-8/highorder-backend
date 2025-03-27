package kt8.order_service.dto.response;

import kt8.order_service.entity.Order;
import kt8.order_service.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateResponse {

    private Long orderId;
    private OrderStatus orderStatus;    // ex) IN_PROGRESS
    private String orderTable;          // "23번 테이블"
    private Long tableId;               // 23
    private Integer orderCode;          // <--- 추가
    private Long totalAmount;           // 26000
    private LocalDateTime createdAt;
    private List<OrderItemResponseDto> orderItems;

    public static OrderCreateResponse of(Order order, List<OrderItemResponseDto> orderItems) {
        return OrderCreateResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .orderTable(order.getOrderTableName())
                .tableId(order.getTableId())
                .orderCode(order.getOrderCode())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedTime())
                .orderItems(orderItems)
                .build();
    }
}
