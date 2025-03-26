package kt8.restaurant_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCategoryDto {
    private Long menuCategoryId;
    private String menuCategoryName;
    private Integer displayOrder;
}
