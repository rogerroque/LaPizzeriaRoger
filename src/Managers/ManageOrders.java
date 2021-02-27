package Managers;

import java.io.IOException;
import java.text.ParseException;
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
import Entities.Orders;

public class ManageOrders {

	public static EntityManagerFactory factory;

	public static void start() throws IOException, ParseException {
		try {
			factory = Persistence.createEntityManagerFactory("LaPizzeriaRoger");
		} catch (Throwable ex) {
			System.err.println("Failed to create EntityManagerFactory object."
					+ ex);
			throw new ExceptionInInitializerError(ex);
		}
		ManageOrders MOrder = new ManageOrders();
		FileAccessor fa;
		fa = new FileAccessor();
		fa.readOrdersFile("Orders.csv");
		System.out.println("OrderDetails llegits des del fitxer");
		for (int i = 0; i < fa.listaOrders.size(); i++) {
			System.out.println(fa.listaOrders.get(i).toString());
			MOrder.addOrder(fa.listaOrders.get(i));
		}
		System.out.println("OrderDetails llegits de la base de dades");
		MOrder.listOrder();
		
		/*-----------------------------------------------------------------------------------------------------*/
		/* Metodo delete y update NO FUNCIONAN BIEN, siempre da un error al actualizar o borrar la ultima fila */ 
		
		/*MOrder.deleteOrder(5);
		MOrder.updateOrder(12);*/
		
		/*-----------------------------------------------------------------------------------------------------*/
		
		System.out.println("OrderDetails llegits de la base de dades després de des actualitzacions");
		MOrder.listOrder();
	}

	public void addOrder(Orders order) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(order);
		em.getTransaction().commit();
		em.close();
	}

	public void listOrder() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		List<Orders> result = em.createQuery("from Orders", Orders.class)
				.getResultList();
		for (Orders order : result) {
			System.out.println(order.toString());
		}
		em.getTransaction().commit();
		em.close();

	}

	public void updateOrder(Integer orderDetailId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Orders order = (Orders) em.find(Orders.class, orderDetailId);
		em.merge(order);
		em.getTransaction().commit();
		em.close();
	}

	public void deleteOrder(Integer orderDetailId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Orders orderDetails = (Orders) em.find(Orders.class, orderDetailId);
		em.remove(orderDetailId);
		em.getTransaction().commit();
		em.close();
	}
}
