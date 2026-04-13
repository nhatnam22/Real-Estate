package com.project.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.java.model.WishList;

@Service
public interface WishListService {
    WishList addPropertyToWishList(Long propertyId, String userEmail) throws Exception;
    void removePropertyFromWishList(Long propertyId, String userEmail) throws Exception;
    List<WishList> getUserWishList(String userEmail) throws Exception;
}
