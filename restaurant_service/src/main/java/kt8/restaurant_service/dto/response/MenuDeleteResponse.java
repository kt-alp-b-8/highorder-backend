package kt8.restaurant_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDeleteResponse {

    private Long menuId;

    public static MenuDeleteResponse of(Long menuId) {
        return new MenuDeleteResponse(menuId);
    }
}