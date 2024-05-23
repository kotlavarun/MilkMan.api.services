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
@Table(name = "transaction_master", schema = "sales")
public class TransactionMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id")
    private int tId;
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "cust_id")
    private int custId;
    @Column(name = "emp_id")
    private int empId;
    @Column(name = "received_amount")
    private BigDecimal receivedAmount;
    @Column(name = "received_date")
    private LocalDateTime receivedDate;
    @Column(name = "remark")
    private String remark;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
