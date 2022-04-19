package myproject.hql.test;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Test {

	public static void main(String[] args) {
		
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		try {
			Configuration cfg = new Configuration();
			cfg.configure("/myproject/hql/resources/hibernate.cfg.xml");
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
			builder= builder.applySettings(cfg.getProperties());
			StandardServiceRegistry registry = builder.build();
			sessionFactory = cfg.buildSessionFactory(registry);
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			/*
			Query query = session.createQuery("insert into Employee2(eno,ename,esal,eaddr)select e.eno, e.ename,e.esal,e.eaddr from Employee1 as e");
			int row = query.executeUpdate();
			System.out.println(row + " rows effected");
			*/
			Query query = session.createQuery("select e.eno,e.ename,e.esal,e,eaddr from Employee1 as e order by e.ename desc");
			List<Object[]> list = query.list();
			System.out.println("ENO\tENAME\tESAL\tEADDR");
			System.out.println("----------------------------------");
			for(Object[] objs:list) {
				for(Object obj : objs) {
					System.out.print( obj + "\t");
				}
				System.out.println();
			}
			
			tx.commit();
		}catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
			sessionFactory.close();
		}
		
		
		
	}
}
