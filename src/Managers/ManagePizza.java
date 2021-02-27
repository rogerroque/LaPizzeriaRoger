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
import Entities.Orders;
import Entities.Pizza;

public class ManagePizza {

	public static EntityManagerFactory factory;

	public static void start() throws IOException {
		try {
			factory = Persistence.createEntityManagerFactory("LaPizzeriaRoger");
		} catch (Throwable ex) {
			System.err.println("Failed to create EntityManagerFactory object."
					+ ex);
			throw new ExceptionInInitializerError(ex);
		}
		ManagePizza MPizza = new ManagePizza();
		FileAccessor fa;
		fa = new FileAccessor();
		fa.readPizzaFile("Pizzas.csv");
		System.out.println("OrderDetails llegits des del fitxer");
		for (int i = 0; i < fa.listaPizza.size(); i++) {
			System.out.println(fa.listaPizza.get(i).toString());
			MPizza.addPizza(fa.listaPizza.get(i));
		}
		System.out.println("Pizzas llegits de la base de dades");
		MPizza.listPizza();
		
		/*-----------------------------------------------------------------------------------------------------*/
		/* Metodo delete y update NO FUNCIONAN BIEN, siempre da un error al actualizar o borrar la ultima fila */ 
		
		/*MPizza.deletePizza(5);
		MPizza.updatePizza(10);*/
		
		/*-----------------------------------------------------------------------------------------------------*/
		
		System.out.println("Pizzas llegits de la base de dades després de des actualitzacions");
		MPizza.listPizza();
	}

	/* Method to CREATE an Autor in the database */
	public void addPizza(Pizza pizza) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(pizza);
		em.getTransaction().commit();
		em.close();
	}

	/* Method to READ all Autors */
	public void listPizza() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		List<Pizza> result = em.createQuery("from Pizza", Pizza.class)
				.getResultList();
		for (Pizza pizza : result) {
			System.out.println(pizza.toString());
		}
		em.getTransaction().commit();
		em.close();

	}

	/* Method to UPDATE activity for an autor */
	public void updatePizza(Integer pizzaId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Pizza pizza = (Pizza) em.find(Pizza.class, pizzaId);
		em.merge(pizzaId);
		em.getTransaction().commit();
		em.close();
	}

	/* Method to DELETE an employee from the records */
	public void deletePizza(Integer pizzaId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Pizza pizza = (Pizza) em.find(Pizza.class, pizzaId);
		em.remove(pizzaId);
		em.getTransaction().commit();
		em.close();
	}
}
