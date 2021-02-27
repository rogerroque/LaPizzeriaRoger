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
import Entities.IngredientPizza;

public class ManageIngredientPizza {

	public static EntityManagerFactory factory;

	public static void start() throws IOException {
		try {
			factory = Persistence.createEntityManagerFactory("LaPizzeriaRoger");
		} catch (Throwable ex) {
			System.err.println("Failed to create EntityManagerFactory object."
					+ ex);
			throw new ExceptionInInitializerError(ex);
		}
		ManageIngredientPizza MIngredientPizza = new ManageIngredientPizza();
		FileAccessor fa;
		fa = new FileAccessor();
		fa.readIngredientPizzaFile("IngredientPizza.csv");
		System.out.println("IngredientPizzza llegits des del fitxer");
		for (int i = 0; i < fa.listaIngredientPizza.size(); i++) {
			System.out.println(fa.listaIngredientPizza.get(i).toString());
			MIngredientPizza.addIngredientPizza(fa.listaIngredientPizza.get(i));
		}
		System.out.println("IngredientPizzza llegits de la base de dades");
		MIngredientPizza.listIngredientPizza();
		
		/*-----------------------------------------------------------------------------------------------------*/
		/* Metodo delete y update NO FUNCIONAN BIEN, siempre da un error al actualizar o borrar la ultima fila */ 
		
		/*MIngredientPizza.deleteIngredientPizza(5);*/
		/*MIngredientPizza.updateIngredientPizza(12);*/
		
		/*-----------------------------------------------------------------------------------------------------*/

		System.out
				.println("IngredientPizzza llegits de la base de dades després de des actualitzacions");
		MIngredientPizza.listIngredientPizza();
	}

	public void addIngredientPizza(IngredientPizza ingredientPizza) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(ingredientPizza);
		em.getTransaction().commit();
		em.close();
	}

	public void listIngredientPizza() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		List<IngredientPizza> result = em.createQuery("from IngredientPizza", IngredientPizza.class)
				.getResultList();
		for (IngredientPizza ingredientPizza : result) {
			System.out.println(ingredientPizza.toString());
		}
		em.getTransaction().commit();
		em.close();

	}

	public void updateIngredientPizza(Integer ingredientPizzaId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		IngredientPizza ingredientPizza = (IngredientPizza) em.find(IngredientPizza.class, ingredientPizzaId);
		em.merge(ingredientPizza);
		em.getTransaction().commit();
		em.close();
	}

	public void deleteIngredientPizza(Integer ingredientPizzza) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		IngredientPizza ingredientPizza = (IngredientPizza) em.find(IngredientPizza.class, ingredientPizzza);
		em.remove(ingredientPizza);
		em.getTransaction().commit();
		em.close();
	}
}
