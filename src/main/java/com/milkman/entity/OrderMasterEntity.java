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
@Table(name = "orders_master", schema = "sales")
public class OrderMasterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "order_date")
	private LocalDateTime orderDate;

	@Column(name = "cust_id")
	private int custId;

	@Column(name = "order_total_value")
	private BigDecimal orderTotalValue;

	@Column(name = "remark")
	private String remark;

	@Column(name = "is_regular_order")
	private int isRegularOrder;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;

}
