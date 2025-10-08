package co.com.bancounion.inventario.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
	
	 	@Id
	    @GeneratedValue
	    private UUID id;

	    @Column(nullable = false)
	    private String name;

	    @Column(unique = true)
	    private String document;

	    private String email;
	    private String phone;

	    private String address;

	    // Relación con ventas
	    @OneToMany(mappedBy = "customer")
	    private List<Sale> sales;

	    // Getters & Setters
	    public UUID getId() { return id; }
	    public void setId(UUID id) { this.id = id; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getDocument() { return document; }
	    public void setDocument(String document) { this.document = document; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getPhone() { return phone; }
	    public void setPhone(String phone) { this.phone = phone; }

	    public String getAddress() { return address; }
	    public void setAddress(String address) { this.address = address; }

	    public List<Sale> getSales() { return sales; }
	    public void setSales(List<Sale> sales) { this.sales = sales; }
}
