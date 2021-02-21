package com.codeEra.code_era;

import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.codeEra.code_era.model.Bug;
 
/**
 * Test session connection between Hibernate and with the given 
 * Oracle database connection string and credentials. 
 */
public class HibernateOracleTestXML {
 
   public static void main(String[] args) {
       final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
               .configure() // configures settings from hibernate.cfg.xml
               .build();
       try {
    	   // Get Hibernate settings in META-INF hibernate.xml 
           SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
           Session session = factory.openSession();
           Transaction transaction = session.beginTransaction();
            
           // Add a new Bug into database
           Bug b = new Bug();
           session.save(b);
           // Commit changes
           transaction.commit();
           // Close it
           session.close();
           factory.close();
           
       } catch (Exception ex) {
    	   // Return connection error
           StandardServiceRegistryBuilder.destroy(registry);
       }
   }
 
}