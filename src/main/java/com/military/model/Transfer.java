package com.military.model;


import jakarta.persistence.*;


@Entity
@Table(name = "transfers")

public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "from_base_id", nullable = false)
    private Base fromBase;

    @ManyToOne
    @JoinColumn(name = "to_base_id", nullable = false)
    private Base toBase;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentType equipment;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "transfer_date")
    private java.sql.Timestamp transferDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column
    private String remarks;

    @Column(name = "created_date")
    private java.sql.Timestamp createdDate;
    
    @Transient
    private Integer fromBaseId;

    @Transient
    private Integer toBaseId;

    @Transient
    private Integer equipmentId;

    

	public Integer getFromBaseId() {
		return fromBaseId;
	}

	public void setFromBaseId(Integer fromBaseId) {
		this.fromBaseId = fromBaseId;
	}

	public Integer getToBaseId() {
		return toBaseId;
	}

	public void setToBaseId(Integer toBaseId) {
		this.toBaseId = toBaseId;
	}

	public Integer getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Base getFromBase() {
		return fromBase;
	}

	public void setFromBase(Base fromBase) {
		this.fromBase = fromBase;
	}

	public Base getToBase() {
		return toBase;
	}

	public void setToBase(Base toBase) {
		this.toBase = toBase;
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

	public java.sql.Timestamp getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(java.sql.Timestamp transferDate) {
		this.transferDate = transferDate;
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
