package com.test;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
 
 
 

public class Test {	
	private static SessionFactory sessionFactory;
	static {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) { 
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
		}
	} 
	
	public static void main(String[] args) {
		Session session = sessionFactory.openSession();
		User u1 = new User( "Our very first event!", new Date(),20 );
		User u2 = new User( "A follow up event", new Date(), 56);
        
		Address add1 = new Address(u1,"上海","闵行");
		Address add2 = new Address(u1,"上海","黄埔");
		Address add3 = new Address(u2,"北京","密云");
		
		u1.getAdresses().add(add1);
		u1.getAdresses().add(add2);
		
		u2.getAdresses().add(add3);

		session.beginTransaction();
		
		session.save(u1);
		session.save(u2);
		
		session.getTransaction().commit();
        
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
 
	}
	
	
	
	

}


/*
 
   
   
		User u1 =  new User( "Our very first event!", new Date(),20 );
		User u2 = new User( "A follow up event", new Date(), 56);
		User u3 = new User( "三个", new Date(), 8);
		//添
		
		session.beginTransaction();
		session.save(u1);
		session.save(u2 );
		session.save(u3 );
		session.getTransaction().commit();
		session.close();
		
		session = sessionFactory.openSession();
		u1.setUserName("hhsh");
		session.beginTransaction();
		//改
		session.saveOrUpdate(u1);
		//删
		session.delete(u3);
		session.getTransaction().commit();
		session.close();
		
		
		session = sessionFactory.openSession();
        session.beginTransaction();
        //查
        List result = session.createQuery( "from User" ).list();
		for ( User user : (List<User>) result ) {
			System.out.println( "USER (" + user.getRegDate() + ") : " + user.getUserName() );
		}
        session.getTransaction().commit();
        session.close();
        
        session = sessionFactory.openSession();
        session.refresh(u1);
 */
//Session session = sessionFactory.openSession();
//session.beginTransaction();
//User getUser = session.get(User.class, 2L);
//System.out.println(getUser);        
//session.getTransaction().commit();
//
//getUser.setAge(200);
//
//session = sessionFactory.openSession();
//session.beginTransaction();
//User getUser2 = session.get(User.class, 2L);
//session.merge(getUser);
//session.update(getUser2);
//session.getTransaction().commit();


//AuditReader auditReader =  AuditReaderFactory.get(session);
//User user = auditReader.find(User.class, 1l, new Date());
//System.out.println("审计记录："+user);
//session.close();