package kt8.restaurant_service.dto.response;

import kt8.restaurant_service.dto.MenuCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantCategoryInfoResponse {

    private List<MenuCategoryDto> data; // 카테고리 목록
}

