package com.milkman.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "customer_product_price", schema = "sales")
public class CustomerProductPriceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cpp_id")
	private int cppId;

	@Column(name = "cust_id")
	private int custId;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "price")
	private BigDecimal productPrice;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "is_active")
	private int isActive;

}
