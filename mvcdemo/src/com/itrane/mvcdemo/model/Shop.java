package com.itrane.mvcdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "shops")
public class Shop extends AbstractPersistentEntity {

	private static final long serialVersionUID = 1L;

	@Column(length=20)
    @NotNull @Size(min = 1, max = 20)
	private String name;

	@Column(length=13)
	@Size(min = 10, max = 13)
	private String phone;
	
	@Column(length=50)
	@Size(max = 50)
	private String email;

	@Column(length=10)
	@Size(max = 10) @NotNull @NotEmpty
	private String kaitenBi;
	
	@Column(name = "employees_number")
	@Min(value=5) @Max(value=100)
	private Integer emplNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEmplNumber() {
		return emplNumber;
	}

	public void setEmplNumber(Integer emplNumber) {
		this.emplNumber = emplNumber;
	}

	public String getKaitenBi() {
		return kaitenBi;
	}

	public void setKaitenBi(String kaitenBi) {
		this.kaitenBi = kaitenBi;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Shop [id=" + id + ", version=" + version + ", name=" + name
				+ ", phone=" + phone + ", email=" + email + ", kaitenBi="
				+ kaitenBi + ", emplNumber=" + emplNumber + "]";
	}
	
}
