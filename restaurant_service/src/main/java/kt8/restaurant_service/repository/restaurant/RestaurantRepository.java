package kt8.restaurant_service.repository.restaurant;

import kt8.restaurant_service.entity.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // 식당 이름으로 식당 조회
    Optional<Restaurant> findByRestaurantName(String restaurantName);
}

