package org.example.backend.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.example.backend.exception.BadRequestException;
import org.example.backend.exception.InvalidArgumentException;
import org.example.backend.exception.NotFoundException;
import org.example.backend.model.OrderCompleted;
import org.example.backend.model.dto.OrderCompletedDTO;
import org.example.backend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.fasterxml.jackson.core.JsonProcessingException;
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


@RestController
@RequestMapping("/api")
public class OrderController {
    private final ObjectMapper objectMapper;
    private final PaypalServerSdkClient client;
    private final OrderService orderService;

    private static final String BAD_REQUEST_MESSAGE_FORMAT = "Anfrage ist nicht korrekt.";

    public OrderController(ObjectMapper objectMapper, PaypalServerSdkClient client, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.client = client;
        this.orderService = orderService;
    }

    @GetMapping("/orders/completed/{email}")
    public List<OrderCompleted> getOrdersByEmail(@PathVariable String email) throws InvalidArgumentException, NotFoundException {
        return orderService.getOrdersByEmail(email);
    }
		    
   @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> request) throws BadRequestException {
        try {
            String cart = objectMapper.writeValueAsString(request.get("cart"));
            Order response = createOrder(cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(BAD_REQUEST_MESSAGE_FORMAT);
        }

        // try {
        //     String cart = objectMapper.writeValueAsString(request.get("cart"));
        //     Order response = createOrder(cart); // Diese Methode kann ApiException werfen!
        //     return ResponseEntity.ok(response);
        // } catch (JsonProcessingException e) {
        //     throw new BadRequestException(BAD_REQUEST_MESSAGE_FORMAT);
        // } catch (ApiException e) {
        //     // Optional: eigene Behandlung, z.B. 400 oder 500
        //     throw new BadRequestException("Fehler bei der Order-Erstellung: " + e.getMessage());
        // } catch (IOException e) {
        //     // Sollte eigentlich durch JsonProcessingException abgedeckt sein, aber falls doch:
        //     throw new BadRequestException("Fehler beim Verarbeiten der Anfrage.");
        // }
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
                           "1"
                       )
                       .breakdown(
                           new AmountBreakdown.Builder()
                           .itemTotal(
                               new Money(
                                   "USD",
                                   "1"
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
                               new Money.Builder("USD","1").build(),
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
		    
   @PostMapping("/orders/{orderID}/capture")
    public ResponseEntity<Order> captureOrder(@PathVariable String orderID) throws BadRequestException {

        try {
            Order response = captureOrders(orderID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(BAD_REQUEST_MESSAGE_FORMAT);
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
		    
   @PostMapping("/order/completed")
    public ResponseEntity<String> addOrder(@RequestBody OrderCompletedDTO request) throws BadRequestException {
        return orderService.addOrder(request);
    }
}
