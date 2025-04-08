package com.example.javaspringbootnavy1.controller;

import com.example.javaspringbootnavy1.modal.Cart;
import com.example.javaspringbootnavy1.modal.CartItem;
import com.example.javaspringbootnavy1.modal.Product;
import com.example.javaspringbootnavy1.modal.User;
import com.example.javaspringbootnavy1.request.AddItemRequest;
import com.example.javaspringbootnavy1.response.ApiResponse;
import com.example.javaspringbootnavy1.service.CartItemService;
import com.example.javaspringbootnavy1.service.CartService;
import com.example.javaspringbootnavy1.service.ProdcutService;
import com.example.javaspringbootnavy1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProdcutService prodcutService;

    @GetMapping
    public ResponseEntity<Cart> findUserCartByHandler(@RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);

        Cart cart= cartService.findUserCart(user);

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addCartItem(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {

        User user= userService.findUserByJwtToken(jwt);
        Product product= prodcutService.findProductById(req.getProductId());

        CartItem item = cartService.addCartItem(user,product, req.getSize(), req.getQuantity());

        ApiResponse res = new ApiResponse();
        res.setMessage("Mặt hàng được thêm thành công ");

        return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt )
            throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Xóa mặt hàng thành công ");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user= userService.findUserByJwtToken(jwt);

        CartItem updateCartItem = null;
        if (cartItem.getQuantity()>0){
            updateCartItem=cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        }
        return new ResponseEntity<>(updateCartItem, HttpStatus.ACCEPTED);
    }
}
