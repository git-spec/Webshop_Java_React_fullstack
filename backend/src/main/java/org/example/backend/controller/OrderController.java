package org.example.backend.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.controllers.OrdersController;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.http.response.ApiResponse;
import com.paypal.sdk.models.AmountBreakdown;
import com.paypal.sdk.models.AmountWithBreakdown;
import com.paypal.sdk.models.CaptureOrderInput;
import com.paypal.sdk.models.CheckoutPaymentIntent;
import com.paypal.sdk.models.CreateOrderInput;
import com.paypal.sdk.models.Item;
import com.paypal.sdk.models.ItemCategory;
import com.paypal.sdk.models.Money;
import com.paypal.sdk.models.Order;
import com.paypal.sdk.models.OrderRequest;
import com.paypal.sdk.models.PurchaseUnitRequest;


@Controller
@RequestMapping("/api")
public class OrderController {
    private final ObjectMapper objectMapper;
    private final PaypalServerSdkClient client;

    public OrderController(ObjectMapper objectMapper, PaypalServerSdkClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

		    
   @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> request) {
        try {
            String cart = objectMapper.writeValueAsString(request.get("cart"));
            Order response = createOrder(cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Order createOrder(String cart) throws IOException, ApiException {
        CreateOrderInput createOrderInput = new CreateOrderInput.Builder(
           null,
           new OrderRequest.Builder(
               CheckoutPaymentIntent.fromString("CAPTURE"),
               Arrays.asList(
               new PurchaseUnitRequest.Builder(
                       new AmountWithBreakdown.Builder(
                           "USD",
                           "100"
                       )
                       .breakdown(
                           new AmountBreakdown.Builder()
                           .itemTotal(
                               new Money(
                                   "USD",
                                   "100"
                               )
                           ).build()
                       )
                       .build()
                   )
                   .items(
                       // lookup item details in `cart` from database
                       Arrays.asList(
                           new Item.Builder(
                               "T-Shirt",
                               new Money.Builder("USD","100").build(),
                               "1"
                           )
                           .description("Super Fresh Shirt")
                           .sku("sku01")
                           .category(ItemCategory.PHYSICAL_GOODS)
                           .build()
                          )
                   )
                   
               .build()
               )
           )
           
           
           .build()
       ).build();
       OrdersController ordersController = client.getOrdersController();
       ApiResponse<Order> apiResponse = ordersController.createOrder(createOrderInput);
       return apiResponse.getResult();
    }   
		    
   @PostMapping("/api/orders/{orderID}/capture")
            public ResponseEntity<Order> captureOrder(@PathVariable String orderID) {
                try {
                    Order response = captureOrders(orderID);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            private Order captureOrders(String orderID) throws IOException, ApiException {
                CaptureOrderInput ordersCaptureInput = new CaptureOrderInput.Builder(
                        orderID,
                        null)
                        .build();
                OrdersController ordersController = client.getOrdersController();
                ApiResponse<Order> apiResponse = ordersController.captureOrder(ordersCaptureInput);
                return apiResponse.getResult();
            }
}
