package com.microsecond.inventoryservice.controller;

import com.microsecond.inventoryservice.dto.InventoryResponseDto;
import com.microsecond.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto> isInStock(@RequestParam List<String>  skuCode){
        return inventoryService.isInStock(skuCode);
    }


}
