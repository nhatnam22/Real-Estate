package com.project.java.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name="Pcomment_id")
    private Comment pComment; 
    
    @ManyToOne
    @JoinColumn(name="property_id")
    private Property property; 
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    private LocalDateTime time;

    // Private constructor to enforce the use of the Builder
    private Comment(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.pComment = builder.pComment;
        this.property = builder.property;
        this.user = builder.user;
        this.time = builder.time;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public Comment getpComment() {
        return pComment;
    }
    public Property getProperty() {
        return property;
    }
    public User getUser() {
        return user;
    }
    public LocalDateTime getTime() {
        return time;
    }
    
    // Builder class
    public static class Builder {
        private Long id;
        private String description;
        private Comment pComment;
        private Property property;
        private User user;
        private LocalDateTime time;
        
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder pComment(Comment pComment) {
            this.pComment = pComment;
            return this;
        }
        public Builder property(Property property) {
            this.property = property;
            return this;
        }
        public Builder user(User user) {
            this.user = user;
            return this;
        }
        public Builder time(LocalDateTime time) {
            this.time = time;
            return this;
        }
        
        public Comment build() {
            return new Comment(this);
        }
    }
    
    // Static method to get a new builder instance
    public static Builder builder() {
        return new Builder();
    }
}
