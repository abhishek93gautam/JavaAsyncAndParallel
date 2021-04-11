package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class CheckoutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);


    @Test
    void no_of_cores() {
        System.out.println("No of cores : " + Runtime.getRuntime().availableProcessors());
    }

    @Test
    void parallelism() {
        System.out.println("Parallelism : " + ForkJoinPool.getCommonPoolParallelism());
    }

    @Test
    void checkout_6_items() {
        //given
        Cart cart = DataSet.createCart(6);

        //when
        CheckoutResponse checkoutResponse =  checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
        assertTrue(checkoutResponse.getFinalRate() > 0);
    }

    @Test
    void checkout_16_items() {
        //given
        Cart cart = DataSet.createCart(16);

        //when
        CheckoutResponse checkoutResponse =  checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_25_items() {
        //given
        Cart cart = DataSet.createCart(25);

        //when
        CheckoutResponse checkoutResponse =  checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }


}