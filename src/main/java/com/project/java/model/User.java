package com.project.java.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class User implements UserDetails {


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		roles.stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stubs
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="email")
	private String email;
	
	@Column (name = "name")
	private String name;
	
	@Column(name ="password")
	private String password;
	
	@Column (name = "phone")   
	private String phone;
	
	@Column (name = "money")   
	private Float money;
	
	@Column (name =" googleAccountId")
	private String googleAccountId;
	
	@Column (name =" facebookAccountId")
	private String facebookAccountId;
	
	@Column (name ="refreshAccessToken")
	private String refreshAccessToken;
	
	@Column (name ="citizenIdentityCard")
	private ArrayList<String> citizenIdentityCard;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonManagedReference
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(mappedBy ="postedBy")
	private Set<Property> properties; 
	
	@OneToMany(mappedBy ="user")
	private Set<Comment> comments;
	
	@OneToMany(mappedBy="user")
	private Set<Submission> submissions;
	
	@OneToMany(mappedBy ="user")
	private Set<Rating> ratings;
	
	@OneToMany(mappedBy ="user")
	private Set<WishList> wishList;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGoogleAccountId() {
		return googleAccountId;
	}

	public void setGoogleAccountId(String googleAccountId) {
		this.googleAccountId = googleAccountId;
	}

	public String getFacebookAccountId() {
		return facebookAccountId;
	}

	public void setFacebookAccountId(String facebookAccountId) {
		this.facebookAccountId = facebookAccountId;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRefreshAccessToken() {
		return refreshAccessToken;
	}

	public void setRefreshAccessToken(String refreshAccessToken) {
		this.refreshAccessToken = refreshAccessToken;
	}
    // Private constructor to enforce use of Builder
    private User(UserBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.name = builder.name;
        this.password = builder.password;
        this.phone = builder.phone;
        this.googleAccountId = builder.googleAccountId;
        this.facebookAccountId = builder.facebookAccountId;
        this.refreshAccessToken = builder.refreshAccessToken;
        this.roles = builder.roles;
        this.citizenIdentityCard = builder.citizenIdentityCard;
    }

    // Static method to get an instance of the Builder
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String email;
        private String name;
        private String password;
        private String phone;
        private String googleAccountId;
        private String facebookAccountId;
        private String refreshAccessToken;
        private Set<Role> roles = new HashSet<>();
        private ArrayList<String> citizenIdentityCard = new ArrayList<>();

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder googleAccountId(String googleAccountId) {
            this.googleAccountId = googleAccountId;
            return this;
        }

        public UserBuilder facebookAccountId(String facebookAccountId) {
            this.facebookAccountId = facebookAccountId;
            return this;
        }

        public UserBuilder refreshAccessToken(String refreshAccessToken) {
            this.refreshAccessToken = refreshAccessToken;
            return this;
        }

        public UserBuilder citizenIdentityCard(ArrayList<String> citizenIdentityCard) {
            this.citizenIdentityCard = citizenIdentityCard;
            return this;
        }
        
        public UserBuilder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

	public User() {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.googleAccountId = googleAccountId;
		this.facebookAccountId = facebookAccountId;
		this.refreshAccessToken = refreshAccessToken;
		this.roles = roles;
		this.properties = properties;
		this.comments = comments;
		this.submissions = submissions;
		this.citizenIdentityCard= citizenIdentityCard;
		
	}

	public ArrayList<String> getCitizenIdentityCard() {
		return citizenIdentityCard;
	}

	public void setCitizenIdentityCard(ArrayList<String> citizenIdentityCard) {
		this.citizenIdentityCard = citizenIdentityCard;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}
	
	

}
