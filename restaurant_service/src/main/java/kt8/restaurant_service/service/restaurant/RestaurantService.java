package kt8.restaurant_service.service.restaurant;

import kt8.restaurant_service.dto.MenuCategoryDto;
import kt8.restaurant_service.dto.MenuDto;
import kt8.restaurant_service.dto.TableDto;
import kt8.restaurant_service.dto.response.RestaurantCategoryInfoResponse;
import kt8.restaurant_service.dto.response.RestaurantInfoResponse;
import kt8.restaurant_service.dto.response.RestaurantMenuInfoResponse;
import kt8.restaurant_service.dto.response.RestaurantTableInfoResponse;
import kt8.restaurant_service.entity.menu.Menu;
import kt8.restaurant_service.entity.menu.MenuCategory;
import kt8.restaurant_service.entity.restaurant.Restaurant;
import kt8.restaurant_service.entity.restaurant.RestaurantTable;
import kt8.restaurant_service.exception.CustomException;
import kt8.restaurant_service.exception.ErrorCode;
import kt8.restaurant_service.repository.menu.MenuCategoryRepository;
import kt8.restaurant_service.repository.menu.MenuRepository;
import kt8.restaurant_service.repository.restaurant.RestaurantRepository;
import kt8.restaurant_service.repository.restaurant.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    public RestaurantCategoryInfoResponse getMenuCategories(Long restaurantId, String sortParam, String lang) {

        // 1) 식당 존재 여부 확인
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 식당을 찾을 수 없습니다."));

        // 2) 카테고리 목록 조회
        // 예: sortParam이 "displayOrder"라면 displayOrder 기준 정렬
        //     sortParam이 null 또는 다른 값이라면 그냥 기본 정렬(예: id 순)
        List<MenuCategory> categories;
        if ("displayOrder".equals(sortParam)) {
            categories = menuCategoryRepository.findByRestaurant_IdOrderByDisplayOrderAsc(restaurantId);
        } else {
            // 기본 정렬 (예: menuCategoryId asc) or 커스텀 로직
            categories = menuCategoryRepository.findByRestaurant_Id(restaurantId);
        }

        // 3) DTO 변환
        List<MenuCategoryDto> dtoList = categories.stream()
                .map(category -> MenuCategoryDto.builder()
                        .menuCategoryId(category.getId())
                        .menuCategoryName(getCategoryNameByLang(category, lang)) // [CHANGED]
                        .displayOrder(category.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());

        return RestaurantCategoryInfoResponse.builder()
                .data(dtoList)
                .build();
    }

    // [CHANGED] lang에 따라 카테고리명 반환
    private String getCategoryNameByLang(MenuCategory cat, String lang) {

        switch (lang.toLowerCase()) {
            case "en":
                return (cat.getMenuCategoryNameEn() != null && !cat.getMenuCategoryNameEn().isEmpty())
                        ? cat.getMenuCategoryNameEn() : cat.getMenuCategoryName(); // fallback = 한글
            case "zh":
                return (cat.getMenuCategoryNameZh() != null && !cat.getMenuCategoryNameZh().isEmpty())
                        ? cat.getMenuCategoryNameZh() : cat.getMenuCategoryName();
            case "jp":
                return (cat.getMenuCategoryNameJp() != null && !cat.getMenuCategoryNameJp().isEmpty())
                        ? cat.getMenuCategoryNameJp() : cat.getMenuCategoryName();
            default:
                // "kr" or 기타
                return cat.getMenuCategoryName();
        }
    }

    public RestaurantInfoResponse getRestaurantInfo(Long restaurantId, Long tableId) {
        // 1) 식당 조회
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 2) 테이블 조회
        RestaurantTable table = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new CustomException(ErrorCode.TABLE_NOT_FOUND));

//        // 3) 테이블이 해당 식당에 속하는지 확인 (예: table.getRestaurant().getRestaurantId() == restaurantId)
//        if (!table.getRestaurant().getRestaurantId().equals(restaurantId)) {
//            // 식당id 불일치
//            throw new RuntimeException("테이블이 해당 식당에 속해있지 않습니다.");
//        }

        return RestaurantInfoResponse.builder()
                .restaurantName(restaurant.getRestaurantName())
                .tableName(table.getTableName())
                .build();
    }

    public RestaurantMenuInfoResponse getMenuList(Long restaurantId, Long menuCategoryId, String sortParam, String lang) {
//        // 1) 식당 존재 확인
//        Restaurant restaurant = restaurantRepository.findById(restaurantId)
//                .orElseThrow(() -> new RuntimeException("해당 식당을 찾을 수 없습니다."));

        // 2) 카테고리 존재 + 식당 소속 여부 확인
        MenuCategory category = menuCategoryRepository.findById(menuCategoryId)
                .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));

//        if (!category.getRestaurant().getRestaurantId().equals(restaurantId)) {
//            throw new RuntimeException("해당 카테고리가 이 식당에 속해있지 않습니다.");
//        }

        // 3) 메뉴 목록 조회
        List<Menu> menus;
        if ("displayOrder".equals(sortParam)) {
            menus = menuRepository.findByRestaurant_IdAndMenuCategory_IdOrderByDisplayOrderAsc(restaurantId, menuCategoryId);
        } else {
            // 기본 정렬 (예: menu_id asc) or 다른 로직
            menus = menuRepository.findByRestaurant_IdAndMenuCategory_Id(restaurantId, menuCategoryId);
        }

        // 4) DTO 변환
        List<MenuDto> menuDtoList = menus.stream()
                .map(menu -> MenuDto.builder()
                        .menuId(menu.getId())
                        .menuName(getMenuNameByLang(menu, lang)) // [CHANGED]
                        .price(menu.getPrice())
                        .menuDescription(getMenuDescByLang(menu, lang)) // [CHANGED]
                        .menuImageUrl(menu.getMenuImageUrl())
                        .displayOrder(menu.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());

        return RestaurantMenuInfoResponse.builder()
                .data(menuDtoList)
                .build();
    }

    // [CHANGED] lang에 따라 메뉴명 반환
    private String getMenuNameByLang(Menu m, String lang) {

        switch (lang.toLowerCase()) {
            case "en":
                return (m.getMenuNameEn() != null && !m.getMenuNameEn().isEmpty())
                        ? m.getMenuNameEn() : m.getMenuName();
            case "zh":
                return (m.getMenuNameZh() != null && !m.getMenuNameZh().isEmpty())
                        ? m.getMenuNameZh() : m.getMenuName();
            case "jp":
                return (m.getMenuNameJp() != null && !m.getMenuNameJp().isEmpty())
                        ? m.getMenuNameJp() : m.getMenuName();
            default:
                // kr or 기타
                return m.getMenuName();
        }
    }

    // [CHANGED] lang에 따라 메뉴설명 반환
    private String getMenuDescByLang(Menu m, String lang) {

        switch (lang.toLowerCase()) {
            case "en":
                return (m.getMenuDescriptionEn() != null && !m.getMenuDescriptionEn().isEmpty())
                        ? m.getMenuDescriptionEn() : m.getMenuDescription();
            case "zh":
                return (m.getMenuDescriptionZh() != null && !m.getMenuDescriptionZh().isEmpty())
                        ? m.getMenuDescriptionZh() : m.getMenuDescription();
            case "jp":
                return (m.getMenuDescriptionJp() != null && !m.getMenuDescriptionJp().isEmpty())
                        ? m.getMenuDescriptionJp() : m.getMenuDescription();
            default:
                return m.getMenuDescription();
        }
    }

    public RestaurantTableInfoResponse getTableList(Long restaurantId, String sortParam) {
        // 1) 식당 존재 여부 확인
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 식당 정보를 찾을 수 없습니다."));

        // 2) 테이블 목록 조회 (정렬)
        //    여기서는 sortParam="table_id"인 경우만 구현 (확장 가능)
        List<RestaurantTable> tables;
        if ("table_id".equalsIgnoreCase(sortParam)) {
            tables = restaurantTableRepository.findByRestaurantIdOrderByTableIdAsc(restaurantId);
        } else {
            // fallback: 그냥 table_id asc
            tables = restaurantTableRepository.findByRestaurantIdOrderByTableIdAsc(restaurantId);
        }

        // 3) DTO 변환
        List<TableDto> tableDtos = tables.stream()
                .map(table -> TableDto.builder()
                        .tableId(table.getId())
                        .tableName(table.getTableName())
                        .build())
                .collect(Collectors.toList());

        return RestaurantTableInfoResponse.builder()
                .data(tableDtos)
                .build();
    }
}
