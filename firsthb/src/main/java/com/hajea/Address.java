package com.hajea;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

//@Audited
@Entity
@Table( name = "ADDRESS" )
public class Address {
	@Id
	@Column(name = "ADDR_ID")
	@GeneratedValue(generator="myInc")
	@GenericGenerator(name="myInc", strategy = "increment")
    private Long id;
	
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private User user;
    
    private String city;
    
    private String street;

    /*
     * http://stackoverflow.com/questions/4496548/hibernate-insert-cascade-not-inserting-foreign-key
     */
    
    public Address(User user,String city,String street){
    	this.user = user;
    	this.city = city;
    	this.street = street;
    }
    public Address(){
    	
    }
    
    public Address(Long id,User user){
    	this.id = id;
    	this.user = user;
    }
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCity() {
		return city;
	}
 
	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
 
	
	
	
	
	 
	
    
    

	 
}