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
@Table(name = "customer_balance_details", schema = "sales")
public class CustomerBalanceDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cust_bal_id")
	private int cppId;

	@Column(name = "cust_id")
	private int custId;

	@Column(name = "balance")
	private BigDecimal balance;

	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;

}
