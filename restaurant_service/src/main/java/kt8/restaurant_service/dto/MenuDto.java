package kt8.restaurant_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto {
    private Long menuId;
    private String menuName;
    private int price;
    private String menuDescription;
    private String menuImageUrl;
    private Integer displayOrder;
}