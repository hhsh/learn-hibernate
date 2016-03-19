package com.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

//@Audited
@Entity
@Table( name = "USERS" )
public class User {
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(generator="myInc")
	@GenericGenerator(name="myInc", strategy = "increment")
    private Long id;
    
	@Column(name = "USER_NAME")
    private String userName;
    
	
	/*
     * 放在非主键字段的get方法上才有效果
     */
    @NotAudited
    private int age;
    
  // 注释可以在戈塔特人方法上，也可以在字段上，都有效
  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "REG_DATE")
    private Date regDate;
  	
  	/*
  	 * 
  	 * org.hibernate.AnnotationException: Associations marked as mappedBy must not 
  	 * define database mappings like @JoinTable or @JoinColumn: com.test.User.adresses
  	 */
  	//mappedBy="user" 为Address中user属性
  	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,
  			mappedBy="user"  )
  	//@JoinColumn(name="USER_ID") 
    private Set<Address>adresses = new HashSet<Address>(0);
    
   

	public User() {
	}

	public User(String userName, Date regDate,int age) {
		this.userName = userName;
		this.regDate = regDate;
		this.age = age;
	}

	
    public Long getId() {
		return id;
    }

    private void setId(Long id) {
		this.id = id;
    }

    
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
    
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	
	public Set<Address> getAdresses() {
		return adresses;
	}

	public void setAdresses(Set<Address> adresses) {
		this.adresses = adresses;
	}

	public String toString(){
		return this.getId()+ "-" + this.getUserName() + "-" + this.regDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	 
	
	
	 
}