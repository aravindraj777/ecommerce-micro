package com.microsecond.orderservice.service;

import com.microsecond.orderservice.dto.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);

}
