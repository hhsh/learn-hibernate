package com.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
 
 
/*
 * 时间戳 14583-52870212
 * 关于审计REVTYPE
 * 0插入
 * 1修改
 * 2删除
 * 
 *  REV是版本号
 */

/*
 * 
 * 更新，关联删除 http://stackoverflow.com/questions/18358407/org-hibernate-objectdeletedexception-deleted-object-would-be-re-saved-by-cascad
 */
public class Test {	
	private static final Logger LOGGER = Logger.getLogger(Test.class);
	
	private static SessionFactory sessionFactory;
	static {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) { 
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);//创建不带刷新的PrintWriter对象
			e.printStackTrace(pw);//将打印信息输出到指定的pw
			StringBuffer buffer = sw.getBuffer();//得到缓冲区的内容
			LOGGER.info(buffer.toString());
		}
	} 
	//有PERSIST操作级联的插入
	public static void addAll() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		User u1 = new User( "Our very first event!", new Date(),20 );
		User u2 = new User( "A follow up event", new Date(), 56);
        
		Address add1 = new Address(u1,"上海","闵行");
		Address add2 = new Address(u1,"上海","黄埔");
		Address add3 = new Address(u2,"北京","密云");
		
		u1.getAdresses().add(add1);
		u1.getAdresses().add(add2);		
		u2.getAdresses().add(add3);
		
		session.save(u1);
		session.save(u2 );
		
		session.getTransaction().commit();
	}
	//有删除级联的情况下删除
	public static void deleteAll() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
 	 
		Criteria criteria = session.createCriteria(User.class);//.list();
		List<?>  userList = criteria.list();
		for(Object obj:userList){
			session.delete(obj);
		}
//		User user = session.get(User.class,2l);
//		session.delete(user);
//		user = session.get(User.class, 2l);
//		session.delete(user);
		
		session.getTransaction().commit();
	}
	//无级联删除
	public static void deleteAll2() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
 	 
		Criteria criteria = session.createCriteria(User.class);//.list();
		List<?>  userList = criteria.list();
		for(Object obj:userList){
			Set<Address> adds = ((User)obj).getAdresses();
			for(Address add:adds){
				session.delete(add);
			}
			session.delete(obj);
		}
		session.getTransaction().commit();
	}

	//在没有级联删除的情况下，手动删除依附记录，最后删除本记录。
	//当然，如果有删除级联，则可直接删除本记录，依附记录自动被删除。
	public static void manulDelete(Long userId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User user = session.get(User.class, userId);
		Set<Address> adds = user.getAdresses();
		for(Address ad :adds){
			session.delete(ad);
		}
		session.delete(user);
		session.getTransaction().commit();
	}
	
	//在没有级联删除的情况下，手动删除依附记录，最后删除本记录
	public static void manulUpdae(Long userId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User user = session.get(User.class, userId);
		Set<Address> adds = user.getAdresses();
		for(Address ad :adds){
			session.delete(ad);
		}
		adds.clear();//这句必须有
		session.update(user);//这句可以有，也可以无
		session.getTransaction().commit();
	}
	//反向删除，即删除依附记录
	public static void revertDelete(Long addrId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Address ad = new Address();
		ad.setId(addrId);
		session.delete(ad);
		
		session.getTransaction().commit();
	}
	
	public static void resetDB(){
		deleteAll2(); 
		addAll() ;  
	}
	
	public static void main(String[] args) {
		
//				manulDelete(1l);
//		manulUpdae(1l);
//		resetDB();
//		manulUpdae(2L);
		
		revertDelete(1l);
		
		//Address add1 = new Address(4l,user);
		//user.setUserName("黄华山23");
		//session.merge(add1);
		 
		//user.getAdresses().clear(); 
//		Iterator<Address> it = adds.iterator();
//		while(it.hasNext()){
//			Address address = it.next();
//			session.delete(address);
//		}
//		while(it.hasNext()){
//			Address address = it.next();
//			adds.remove(address);
//		}
		//session.save(user);
		
		//System.out.println(adds);
		//adds.remove(add1);//无法同步
		//System.out.println(adds);
		//session.update(user);
		
		//session.getTransaction().commit();
        
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
 
	}
	
	
	
	

}


/*
 
 		User u1 = new User( "Our very first event!", new Date(),20 );
		User u2 = new User( "A follow up event", new Date(), 56);
        
		Address add1 = new Address(u1,"上海","闵行");
		Address add2 = new Address(u1,"上海","黄埔");
		Address add3 = new Address(u2,"北京","密云");
		
		u1.getAdresses().add(add1);
		u1.getAdresses().add(add2);		
		u2.getAdresses().add(add3);
		
		session.save(u1);
		session.save(u2 );
		
		
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