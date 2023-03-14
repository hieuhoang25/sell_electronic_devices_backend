package com.poly.datn.controller;


import static com.poly.datn.controller.router.Router.CART_API.BASE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.datn.dto.request.CartItemRequest;
import com.poly.datn.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "CRUD for Cart (Cart Controller)")
public class CartController {

    final CartService service;
    
    @Operation(summary = "Cart's of auth-user")
    @GetMapping()
    public ResponseEntity<?> getCartOfCurrentUser() {
        return ResponseEntity.ok(service.findCartOfCurrentUser());
    }

    @Operation(summary = "add list of items to auth-user's")
    @PostMapping("/add")
    public ResponseEntity<?> mergeItemsToCart(@Valid @RequestBody List<CartItemRequest> requesItems) {

        return ResponseEntity.ok(service.mergeItemsToCart(requesItems));
    }

    @Operation(summary = "remove all items in cart", description = "Removed items in cart by cart id")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> removeAllItemsInCart(@PathVariable Integer cartId) {
       boolean deleted =  service.deleteAllItemsInCart(cartId);
       return new ResponseEntity<Object>(String.format("%s", deleted? "Clear cart" : "Cart is empty"),  deleted? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Cart's by user id", deprecated = true)
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.findByUserId(userId)) ;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCartById(@PathVariable Integer cartId) {
        return ResponseEntity.ok(service.findByCartId(cartId));
    }

   

   

    
}
