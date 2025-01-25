package com.project.java.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "bedroom")
    private Integer bedroom;

    @Column(name = "bathroom")
    private Integer bathroom;
    
    @Column(name = "floor")
    private Integer floor;

    @Column(name = "year-built")
    private Integer yearBuilt;

    @Column(name = "size")
    private Integer size;

    @Column(name = "images")
    private String images;

    @Column(name = "address")
    private String address;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "avg-star")
    private Integer avgStar;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "expired-date")
    private LocalDateTime expiredDate;

    @Column(name = "expired-boost")
    private LocalDateTime expiredBoost;

    @Column(name = "direction")
    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PropertyStatus status;
    
    @Column(name = "status-review")
    @Enumerated(EnumType.STRING)
    private ReviewStatus statusReview;

    @Column(name = "listing-type")
    @Enumerated(EnumType.STRING)
    private PropertyListingType listingType;

	@ManyToOne
	@JoinColumn(name = "poste-by")
	private User postedBy;

	@Column(name = "feature-image")
	private String featureImage;

	@ManyToOne
	@JoinColumn(name = "property_type_id")
	private PropertyType propertyType;

	@ManyToMany
	@JoinTable(name ="property_feature",
	joinColumns = @JoinColumn(name="feature_id"),
	inverseJoinColumns =@JoinColumn(name="property_id"))
	private List<Feature> features;
	
	@ManyToMany(mappedBy ="property")
	@Fetch(value = FetchMode.SELECT)
	@JsonIgnore
	private Set<Tag> tags = new HashSet<> ();
	
	@OneToMany(mappedBy="property")
	Set<Comment> comments;
	
	@OneToMany(mappedBy="property")
	Set<Rating> ratings;
	
	@OneToMany(mappedBy="property")
	Set<WishList> wishList;
	
    // Constructor riêng tư
    private Property(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.bedroom = builder.bedroom;
        this.bathroom = builder.bathroom;
        this.yearBuilt = builder.yearBuilt;
        this.size = builder.size;
        this.images = builder.images;
        this.address = builder.address;
        this.province = builder.province;
        this.district = builder.district;
        this.ward = builder.ward;
        this.avgStar = builder.avgStar;
        this.verified = builder.verified;
        this.expiredDate = builder.expiredDate;
        this.expiredBoost = builder.expiredBoost;
        this.direction = builder.direction;
        this.status = builder.status;
        this.listingType = builder.listingType;
        this.postedBy = builder.postedBy;
        this.featureImage = builder.featureImage;
        this.propertyType = builder.propertyType;
        this.features = builder.features;
        this.tags = builder.tags;
        this.comments = builder.comments;
        this.ratings = builder.ratings;
        this.wishList = builder.wishList;
        this.statusReview = builder.statusReview;
    }

    // Class Builder
    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private Integer price;
        private Integer bedroom;
        private Integer bathroom;
        private Integer yearBuilt;
        private Integer size;
        private String images;
        private String address;
        private String province;
        private String district;
        private String ward;
        private Integer avgStar;
        private Boolean verified;
        private LocalDateTime expiredDate;
        private LocalDateTime expiredBoost;
        private Direction direction;
        private PropertyStatus status;
        private ReviewStatus statusReview;
        private PropertyListingType listingType;
        private User postedBy;
        private String featureImage;
        private PropertyType propertyType;
        private List<Feature> features = new ArrayList<>();
        private Set<Tag> tags = new HashSet<>();
        private Set<Comment> comments = new HashSet<>();
        private Set<Rating> ratings = new HashSet<>();
        private Set<WishList> wishList = new HashSet<>();

        public Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(Integer price) {
            this.price = price;
            return this;
        }

        public Builder bedroom(Integer bedroom) {
            this.bedroom = bedroom;
            return this;
        }

        public Builder bathroom(Integer bathroom) {
            this.bathroom = bathroom;
            return this;
        }

        public Builder yearBuilt(Integer yearBuilt) {
            this.yearBuilt = yearBuilt;
            return this;
        }

        public Builder size(Integer size) {
            this.size = size;
            return this;
        }

        public Builder images(String images) {
            this.images = images;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder province(String province) {
            this.province = province;
            return this;
        }

        public Builder district(String district) {
            this.district = district;
            return this;
        }

        public Builder ward(String ward) {
            this.ward = ward;
            return this;
        }

        public Builder avgStar(Integer avgStar) {
            this.avgStar = avgStar;
            return this;
        }

        public Builder verified(Boolean verified) {
            this.verified = verified;
            return this;
        }

        public Builder expiredDate(LocalDateTime expiredDate) {
            this.expiredDate = expiredDate;
            return this;
        }

        public Builder expiredBoost(LocalDateTime expiredBoost) {
            this.expiredBoost = expiredBoost;
            return this;
        }

        public Builder direction(Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder status(PropertyStatus status) {
            this.status = status;
            return this;
        }
        
        public Builder statusReview(ReviewStatus statusReview) {
            this.statusReview = statusReview;
            return this;
        }

        public Builder listingType(PropertyListingType listingType) {
            this.listingType = listingType;
            return this;
        }

        public Builder postedBy(User postedBy) {
            this.postedBy = postedBy;
            return this;
        }

        public Builder featureImage(String featureImage) {
            this.featureImage = featureImage;
            return this;
        }

        public Builder propertyType(PropertyType propertyType) {
            this.propertyType = propertyType;
            return this;
        }

        public Builder features(List<Feature> features) {
            this.features = features;
            return this;
        }

        public Builder tags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder comments(Set<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Builder ratings(Set<Rating> ratings) {
            this.ratings = ratings;
            return this;
        }

        public Builder wishList(Set<WishList> wishList) {
            this.wishList = wishList;
            return this;
        }

        public Property build() {
            return new Property(this);
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getBedroom() {
		return bedroom;
	}

	public void setBedroom(Integer bedroom) {
		this.bedroom = bedroom;
	}

	public Integer getBathroom() {
		return bathroom;
	}

	public void setBathroom(Integer bathroom) {
		this.bathroom = bathroom;
	}

	public Integer getYearBuilt() {
		return yearBuilt;
	}

	public void setYearBuilt(Integer yearBuilt) {
		this.yearBuilt = yearBuilt;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public Integer getAvgStar() {
		return avgStar;
	}

	public void setAvgStar(Integer avgStar) {
		this.avgStar = avgStar;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public LocalDateTime getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(LocalDateTime expiredDate) {
		this.expiredDate = expiredDate;
	}

	public LocalDateTime getExpiredBoost() {
		return expiredBoost;
	}

	public void setExpiredBoost(LocalDateTime expiredBoost) {
		this.expiredBoost = expiredBoost;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public PropertyStatus getStatus() {
		return status;
	}

	public void setStatus(PropertyStatus status) {
		this.status = status;
	}

	public ReviewStatus getStatusReview() {
		return statusReview;
	}

	public void setStatusReview(ReviewStatus statusReview) {
		this.statusReview = statusReview;
	}

	public PropertyListingType getListingType() {
		return listingType;
	}

	public void setListingType(PropertyListingType listingType) {
		this.listingType = listingType;
	}

	public User getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}

	public String getFeatureImage() {
		return featureImage;
	}

	public void setFeatureImage(String featureImage) {
		this.featureImage = featureImage;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public Set<WishList> getWishList() {
		return wishList;
	}

	public void setWishList(Set<WishList> wishList) {
		this.wishList = wishList;
	}

    // Getter và Setter nếu cần thiết
}
