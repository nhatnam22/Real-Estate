package com.project.java.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.model.User;
import com.project.java.model.WishList;
import com.project.java.responses.ClassResponse;
import com.project.java.service.WishListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;
    private static final Logger logger = LoggerFactory.getLogger(WishListController.class);

    @PostMapping("/add/{propertyId}")
    public ResponseEntity<ClassResponse> addPropertyToWishList(@PathVariable Long propertyId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            User user = (User) authentication.getPrincipal();

            WishList wishList = wishListService.addPropertyToWishList(propertyId, user.getEmail());

            return ResponseEntity.ok().body(ClassResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Đã thêm vào danh sách yêu thích (Added to wishlist successfully)")
                    .data(wishList)
                    .build());

        } catch (Exception e) {
            logger.error("Error adding to wishlist", e);
            return ResponseEntity.badRequest().body(ClassResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/remove/{propertyId}")
    public ResponseEntity<ClassResponse> removePropertyFromWishList(@PathVariable Long propertyId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            User user = (User) authentication.getPrincipal();

            wishListService.removePropertyFromWishList(propertyId, user.getEmail());

            return ResponseEntity.ok().body(ClassResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Đã xóa khỏi danh sách yêu thích (Removed from wishlist successfully)")
                    .build());

        } catch (Exception e) {
            logger.error("Error removing from wishlist", e);
            return ResponseEntity.badRequest().body(ClassResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<ClassResponse> getUserWishList() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            User user = (User) authentication.getPrincipal();

            List<WishList> wishLists = wishListService.getUserWishList(user.getEmail());

            return ResponseEntity.ok().body(ClassResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Lấy danh sách yêu thích thành công (Get wishlist successfully)")
                    .data(wishLists)
                    .build());

        } catch (Exception e) {
            logger.error("Error getting wishlist", e);
            return ResponseEntity.badRequest().body(ClassResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
}
