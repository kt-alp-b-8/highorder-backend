package kt8.restaurant_service.dto.response;

import kt8.restaurant_service.entity.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuCreateResponse {

    private Long menuId;
    private Long menuCategoryId;

    public static MenuCreateResponse of(Long menuId, Long menuCategoryId) {
        return new MenuCreateResponse(menuId, menuCategoryId);
    }
}

