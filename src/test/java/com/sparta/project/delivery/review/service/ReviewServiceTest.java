package com.sparta.project.delivery.review.service;

import com.sparta.project.delivery.address.Address;
import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.category.entity.Category;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.exception.DeliveryError;
import com.sparta.project.delivery.common.type.City;
import com.sparta.project.delivery.common.type.DeliveryType;
import com.sparta.project.delivery.common.type.OrderStatus;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.menu.entity.Menu;
import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.order.repository.OrderRepository;
import com.sparta.project.delivery.region.entity.Region;
import com.sparta.project.delivery.review.dto.ReviewDto;
import com.sparta.project.delivery.review.entity.Review;
import com.sparta.project.delivery.review.repository.ReviewReportRepository;
import com.sparta.project.delivery.review.repository.ReviewRepository;
import com.sparta.project.delivery.store.dto.StoreDto;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.store.repository.StoreRepository;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewReportRepository reviewReportRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Store store;
    private User user;
    private Review review;
    private Region region;
    private Category category;
    private Address address;
    private ReviewDto reviewDto;
    private Order order;


    @BeforeEach
    void setUp() {
        user = User.builder().userId(1L).username("testUser").role(UserRoleEnum.CUSTOMER).build();
        region = Region.builder().regionId("regionId-123").city(City.SEOUL).siGun("Gangnam").gu("Gangnam")
                .village("Yeoksam").build();
        category = Category.builder().categoryId("categoryId-123").name("Food").build();
        store = Store.builder().storeId("store1").name("Test Store").averageRating(4.0f).user(user).region(region)
                .category(category).reviewCount(2).menus(Set.of()).build();
        address = Address.builder().addressId("address1").user(user).address("우리집").build();
        order = Order.builder().orderId("order1").store(store)
                .address(address)
                .user(user).orderMenuList(List.of()).price(10000L).request("")
                .type(DeliveryType.DELIVERY).status(OrderStatus.DELIVERY_COMPLETED).build();
        review = Review.builder().reviewId("review1").store(store).order(order).user(user)
                .rating(5).comment("Great service!").reportFlag(false).build();
        reviewDto = ReviewDto.from(review);
    }

    @Test
    void 리뷰생성성공_AND_음식점필드수정() {
        //Given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(orderRepository.findByOrderIdAndUser(anyString(), any(User.class))).willReturn(Optional.of(order));
        //When
        reviewService.addReview(reviewDto);
        //Then
        assertEquals(3, store.getReviewCount());
        assertEquals(4.3333335f, store.getAverageRating(), 0.001); // 평점 계산

        verify(storeRepository, times(1)).save(store);
        verify(reviewRepository, times(1)).save(any(Review.class));

        log.info("Updated Store averageRating: {}", store.getAverageRating());
        log.info("Updated Store reviewCount: {}", store.getReviewCount());
    }

    @Test
    void 리뷰생성시_유저_없으면_에러반환() {
        // Arrange
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> reviewService.addReview(reviewDto));
        assertEquals(USER_NOT_FOUND, exception.getError());
    }

    @Test
    void 리뷰생성시_주문_없으면_에러반환() {
        // Arrange
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(orderRepository.findByOrderIdAndUser(anyString(), any(User.class))).willReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> reviewService.addReview(reviewDto));
        assertEquals(REVIEW_CREATION_FAILED_NOT_ORDER, exception.getError());
    }

}