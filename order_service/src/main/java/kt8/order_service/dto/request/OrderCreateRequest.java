package kt8.order_service.dto.request;

import kt8.order_service.entity.Order;
import kt8.order_service.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private String orderTable;
    private List<OrderItemRequestDto> orderItemRequestDtos;
    private long totalAmount; // ex) 26000

    public Order toEntity(OrderStatus initOrderStatus, Long restaurantId, Long tableId, int orderCode) {
        return Order.builder()
                .orderStatus(initOrderStatus)
                .totalAmount(totalAmount)
                .orderCode(orderCode)
                .orderTableName(orderTable)
                .restaurantId(restaurantId)
                .tableId(tableId)
                .build();
    }
}
