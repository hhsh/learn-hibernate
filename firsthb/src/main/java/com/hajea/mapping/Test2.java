package com.hajea.mapping;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test2 {
	private static EntityManagerFactory entityManagerFactory;
	public static void main(String[] args) {
		entityManagerFactory = Persistence.createEntityManagerFactory( "org.hibernate.tutorial.jpa2" );
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		User u1 = new User( "张三", new Date(),25 );
		User u2 = new User( "李四", new Date(), 36);
		
		entityManager.getTransaction().begin();
		entityManager.persist( u1 );
		entityManager.persist( u2 );
		entityManager.getTransaction().commit();
	}

}
