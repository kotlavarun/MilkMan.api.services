package com.milkman.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "order_lines", schema = "sales")
public class OrderLinesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "o_line_id")
	private int orderLineId;

	@Column(name = "order_id")
	private int orderId;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "quantity")
	private double quantity;

	@Column(name = "product_unit_value")
	private BigDecimal productUnitPrice;

	@Column(name = "order_value")
	private BigDecimal orderValue;

}
