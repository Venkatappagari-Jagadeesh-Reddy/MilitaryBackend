package com.military.model;


import jakarta.persistence.*;


@Entity
@Table(name = "assignments")

public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "base_id", nullable = false)
    private Base base;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentType equipment;

    @Column(name = "assigned_to", nullable = false)
    private String assignedTo;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "assign_date")
    private java.sql.Timestamp assignDate;

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

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public java.sql.Timestamp getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(java.sql.Timestamp assignDate) {
		this.assignDate = assignDate;
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
