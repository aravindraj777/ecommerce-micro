package com.microsecond.orderservice.service.impl;

import com.microsecond.orderservice.dto.InventoryResponse;
import com.microsecond.orderservice.dto.OrderLineItemsDto;
import com.microsecond.orderservice.dto.OrderRequest;
import com.microsecond.orderservice.model.Order;
import com.microsecond.orderservice.model.OrderLineItems;
import com.microsecond.orderservice.repository.OrderRepository;
import com.microsecond.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl  implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;


    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

       List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

//        Call inventory service ,and place order if product is in stock

       InventoryResponse[] inventoryResponses =  webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

       boolean allProductsInStock = Arrays.stream(inventoryResponses)
               .allMatch(InventoryResponse::isInStock);


       if(allProductsInStock) {
           orderRepository.save(order);
       }else {
           throw  new IllegalArgumentException("Product Not found");
       }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
