package com.milkman.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "customer_details_master", schema = "sales")
public class CustomerDetailsMasterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cust_id")
	private int custId;

	@Column(name = "cust_name")
	private String custName;

	@Column(name = "cust_shop_name")
	private String custShopName;

	@Column(name = "cust_phone")
	private String custPhone;

	@Column(name = "route_id")
	private int routeId;

	@Column(name = "emp_id")
	private int empId;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;

	@Column(name = "is_active")
	private int isActive;

}
