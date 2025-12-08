package com.military.model;


import jakarta.persistence.*;


@Entity
@Table(name = "assets")

public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "base_id", nullable = false)
    private Base base;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentType equipment;

    @Column(name = "opening_balance")
    private Integer openingBalance;

    @Column(name = "closing_balance")
    private Integer closingBalance;

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

	public Integer getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(Integer openingBalance) {
		this.openingBalance = openingBalance;
	}

	public Integer getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(Integer closingBalance) {
		this.closingBalance = closingBalance;
	}
    
    
    
}

