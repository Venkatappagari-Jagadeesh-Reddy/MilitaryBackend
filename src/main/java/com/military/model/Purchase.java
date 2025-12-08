package com.military.model;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "purchases")

public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "base_id", nullable = false)
    private Base base;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentType equipment;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_cost")
    private BigDecimal unitCost;

    @Column(name = "purchase_date")
    private java.sql.Date purchaseDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column
    private String remarks;

    @Column(name = "created_date")
    private java.sql.Timestamp createdDate;
    @Transient
    private Integer baseId;
    
    @Transient
    private Integer equipmentId;

    public Integer getEquipmentId() {
    	return equipmentId; 
    	}
    public void setEquipmentId(Integer equipmentId) { 
    	this.equipmentId = equipmentId; 
    	}

    
    

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public EquipmentType getEquipment() {
		return equipment;
	}

	public void setEquipment(EquipmentType equipment) {
		this.equipment = equipment;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public java.sql.Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(java.sql.Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public java.sql.Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.sql.Timestamp createdDate) {
		this.createdDate = createdDate;
	}
    
    
}
