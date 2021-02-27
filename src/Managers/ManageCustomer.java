package Managers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import Package.FileAccessor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import Entities.Customers;

public class ManageCustomer {

	public static EntityManagerFactory factory;

	public static void start() throws IOException {
		try {
			factory = Persistence.createEntityManagerFactory("LaPizzeriaRoger");
		} catch (Throwable ex) {
			System.err.println("Failed to create EntityManagerFactory object."
					+ ex);
			throw new ExceptionInInitializerError(ex);
		}
		ManageCustomer MCustomer = new ManageCustomer();
		FileAccessor fa;
		fa = new FileAccessor();
		fa.readCustomersFile("Customers.csv");
		System.out.println("Customers llegits des del fitxer");
		for (int i = 0; i < fa.listaCustomers.size(); i++) {
			System.out.println(fa.listaCustomers.get(i).toString());
			MCustomer.addCustomer(fa.listaCustomers.get(i));
		}
		System.out.println("Customers llegits de la base de dades");
		MCustomer.listCustomers();
		
		/*-----------------------------------------------------------------------------------------------------*/
		/* Metodo delete y update NO FUNCIONAN BIEN, siempre da un error al actualizar o borrar la ultima fila */ 
		
		/*MCustomer.deleteCustomer(5);
		MCustomer.updateCustomer(10);*/
		
		/*-----------------------------------------------------------------------------------------------------*/
		
		
		System.out
				.println("Autors llegits de la base de dades després de des actualitzacions");
		MCustomer.listCustomers();
	}

	/* Method to CREATE an Autor in the database */
	public void addCustomer(Customers customer) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(customer);
		em.getTransaction().commit();
		em.close();
	}

	/* Method to READ all Autors */
	public void listCustomers() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		List<Customers> result = em.createQuery("from Customers", Customers.class)
				.getResultList();
		for (Customers customer : result) {
			System.out.println(customer.toString());
		}
		em.getTransaction().commit();
		em.close();

	}

	/* Method to UPDATE activity for an autor */
	public void updateCustomer(Integer customerId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Customers customers = (Customers) em.find(Customers.class, customerId);
		em.merge(customers);
		em.getTransaction().commit();
		em.close();
	}

	/* Method to DELETE an employee from the records */
	public void deleteCustomer(Integer customerId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Customers customers = (Customers) em.find(Customers.class, customerId);
		em.remove(customers);
		em.getTransaction().commit();
		em.close();
	}
}
