package com.project.java.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.java.exception.CheckDataException;
import com.project.java.model.Property;
import com.project.java.model.User;
import com.project.java.model.WishList;
import com.project.java.repository.PropertyRepository;
import com.project.java.repository.UserRepository;
import com.project.java.repository.WishListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IWishListService implements WishListService {

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    @Override
    @Transactional
    public WishList addPropertyToWishList(Long propertyId, String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CheckDataException("User not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new CheckDataException("Property not found"));

        if (wishListRepository.existsByUserIdAndPropertyId(user.getId(), propertyId)) {
            throw new Exception("Property is already in the wishlist");
        }

        WishList wishList = WishList.builder()
                .user(user)
                .property(property)
                .build();

        return wishListRepository.save(wishList);
    }

    @Override
    @Transactional
    public void removePropertyFromWishList(Long propertyId, String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CheckDataException("User not found"));

        if (!wishListRepository.existsByUserIdAndPropertyId(user.getId(), propertyId)) {
            throw new Exception("Property is not in the wishlist");
        }

        wishListRepository.deleteByUserIdAndPropertyId(user.getId(), propertyId);
    }

    @Override
    public List<WishList> getUserWishList(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CheckDataException("User not found"));

        return wishListRepository.findByUserId(user.getId());
    }
}
