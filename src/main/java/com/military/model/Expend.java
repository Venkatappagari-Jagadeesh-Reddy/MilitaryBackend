package com.military.model;


import jakarta.persistence.*;


@Entity
@Table(name = "expends")

public class Expend {

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

    @Column(name = "expend_date")
    private java.sql.Timestamp expendDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column
    private String remarks;

    @Column(name = "created_date")
    private java.sql.Timestamp createdDate;

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

	public java.sql.Timestamp getExpendDate() {
		return expendDate;
	}

	public void setExpendDate(java.sql.Timestamp expendDate) {
		this.expendDate = expendDate;
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
