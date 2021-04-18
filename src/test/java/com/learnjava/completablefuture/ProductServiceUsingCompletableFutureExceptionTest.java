package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest {

    @Mock
    ProductInfoService pisMock;

    @Mock
    InventoryService isMock;

    @Mock
    ReviewService rsMock;

    @InjectMocks
    ProductServiceUsingCompletableFuture pscf;

    @Test
    void retrieveProductDetails_approach2() {

        String productId = "ABC123";
        when(pisMock.retrieveProductInfo(any())).thenCallRealMethod();
        when(rsMock.retrieveReviews(any())).thenThrow(new RuntimeException("Exception Occurred"));
        when(isMock.retrieveInventory(any())).thenCallRealMethod();

        Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product.getReview());
        assertEquals(0, product.getReview().getNoOfReviews());
        //assertEquals(0, product.getReview().getOverallRating());
    }

    @Test
    void retrieveProductDetails_productInfoServiceError() {

        String productId = "ABC123";
        when(pisMock.retrieveProductInfo(any())).thenThrow(new RuntimeException("Exception Occurred"));
        when(rsMock.retrieveReviews(any())).thenCallRealMethod();

        //then
        Assertions.assertThrows(RuntimeException.class, () -> pscf.retrieveProductDetailsWithInventory_approach2(productId));
    }
}