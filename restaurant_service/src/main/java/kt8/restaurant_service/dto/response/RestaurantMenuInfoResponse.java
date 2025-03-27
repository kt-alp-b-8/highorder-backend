package kt8.restaurant_service.dto.response;

import kt8.restaurant_service.dto.MenuDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantMenuInfoResponse {

    private List<MenuDto> data;  // 메뉴 목록
}
