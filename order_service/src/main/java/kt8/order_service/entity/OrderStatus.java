package kt8.order_service.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    IN_PROGRESS("주문 진행중"), COMPLETED("주문 완료");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
