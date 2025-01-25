package com.project.java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Pricing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "is-auto-accept")
	private Boolean isAutoAccept;
	
	@Column(name = "is-show-description")
	private Boolean isShowDescription;
	
	@Column(name = "priority")
	private Integer priority;
	
	@Column(name = "score-rank")
	private Integer scoreRank;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "expire-date")
	private String expireDate;
}
