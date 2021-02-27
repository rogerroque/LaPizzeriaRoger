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
import Entities.Ingredient;
import Entities.OrderDetails;

public class ManageOrderDetails {

	public static EntityManagerFactory factory;

	public static void start() throws IOException {
		try {
			factory = Persistence.createEntityManagerFactory("LaPizzeriaRoger");
		} catch (Throwable ex) {
			System.err.println("Failed to create EntityManagerFactory object. "+ ex);
			throw new ExceptionInInitializerError(ex);
		}
		ManageOrderDetails MOrderDetails = new ManageOrderDetails();
		FileAccessor fa;
		fa = new FileAccessor();
		fa.readOrderDetailsFile("OrderDetails.csv");
		System.out.println("OrderDetails llegits des del fitxer");
		for (int i = 0; i < fa.listaOrdersDetails.size(); i++) {
			System.out.println(fa.listaOrdersDetails.get(i).toString());
			MOrderDetails.addOrderDetails(fa.listaOrdersDetails.get(i));
		}
		System.out.println("OrderDetails llegits de la base de dades");
		MOrderDetails.listOrderDetails();
		
		/*-----------------------------------------------------------------------------------------------------*/
		/* Metodo delete y update NO FUNCIONAN BIEN, siempre da un error al actualizar o borrar la ultima fila */ 
		
		/*MOrderDetails.deleteOrderDetails(5);
		MOrderDetails.updateOrderDetails(12);*/
		
		/*-----------------------------------------------------------------------------------------------------*/
		
	
		System.out.println("OrderDetails llegits de la base de dades després de des actualitzacions");
		MOrderDetails.listOrderDetails();
	}

	public void addOrderDetails(OrderDetails orderDetails) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(orderDetails);
		em.getTransaction().commit();
		em.close();
	}

	public void listOrderDetails() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		List<OrderDetails> result = em.createQuery("from OrderDetails", OrderDetails.class)
				.getResultList();
		for (OrderDetails orderDetails : result) {
			System.out.println(orderDetails.toString());
		}
		em.getTransaction().commit();
		em.close();

	}

	public void updateOrderDetails(Integer orderDetailId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		OrderDetails orderDetail = (OrderDetails) em.find(OrderDetails.class, orderDetailId);
		em.merge(orderDetail);
		em.getTransaction().commit();
		em.close();
	}

	public void deleteOrderDetails(Integer orderDetailId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		OrderDetails orderDetails = (OrderDetails) em.find(OrderDetails.class, orderDetailId);
		em.remove(orderDetailId);
		em.getTransaction().commit();
		em.close();
	}
}
