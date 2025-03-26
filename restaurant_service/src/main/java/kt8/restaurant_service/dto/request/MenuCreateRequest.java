package kt8.restaurant_service.dto.request;

import kt8.restaurant_service.entity.menu.Menu;
import kt8.restaurant_service.entity.menu.MenuCategory;
import kt8.restaurant_service.entity.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuCreateRequest {

    // 카테고리 정보
    private Long menuCategoryId;       // 기존 카테고리 id (존재 시)
    private String menuCategoryName;   // 한글 카테고리명 (새로 등록 or 번역 업데이트)

    // 메뉴 정보
    private String menuName;         // 한글 메뉴명
    private String menuDescription;  // 한글 메뉴 설명
    private int price;
    private String menuImageUrl;

    public Menu toEntity(Restaurant restaurant, MenuCategory menuCategory) {

        return Menu.builder()
                .menuName(menuName)
                .menuDescription(menuDescription)
                .price(price)
                .menuImageUrl(menuImageUrl)
                .restaurant(restaurant)
                .menuCategory(menuCategory)
                .build();
    }
}
