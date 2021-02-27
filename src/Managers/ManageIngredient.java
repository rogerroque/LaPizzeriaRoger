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

public class ManageIngredient {

	public static EntityManagerFactory factory;

	public static void start() throws IOException {
		try {
			factory = Persistence.createEntityManagerFactory("LaPizzeriaRoger");
		} catch (Throwable ex) {
			System.err.println("Failed to create EntityManagerFactory object. "+ ex);
			throw new ExceptionInInitializerError(ex);
		}
		ManageIngredient MIngredient = new ManageIngredient();
		FileAccessor fa;
		fa = new FileAccessor();
		fa.readIngredientFile("Ingredients.csv");
		System.out.println("Ingredients llegits des del fitxer");
		for (int i = 0; i < fa.listaIngredient.size(); i++) {
			System.out.println(fa.listaIngredient.get(i).toString());
			MIngredient.addIngredient(fa.listaIngredient.get(i));
		}
		System.out.println("Ingredients llegits de la base de dades");
		MIngredient.listIngredients();
		
		/*-----------------------------------------------------------------------------------------------------*/
		/* Metodo delete y update NO FUNCIONAN BIEN, siempre da un error al actualizar o borrar la ultima fila */ 
		
		/*MIngredient.deleteIngredient(5);
		MIngredient.updateIngredient(12);*/
		
		/*-----------------------------------------------------------------------------------------------------*/
		
		System.out
				.println("Ingredients llegits de la base de dades després de des actualitzacions");
		MIngredient.listIngredients();
	}

	public void addIngredient(Ingredient ingredient) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(ingredient);
		em.getTransaction().commit();
		em.close();
	}

	public void listIngredients() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		List<Ingredient> result = em.createQuery("from Ingredient", Ingredient.class)
				.getResultList();
		for (Ingredient ingredient : result) {
			System.out.println(ingredient.toString());
		}	
		em.getTransaction().commit();
		em.close();

	}

	public void updateIngredient(Integer ingredientId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Ingredient ingredient = (Ingredient) em.find(Ingredient.class, ingredientId);
		em.merge(ingredient);
		em.getTransaction().commit();
		em.close();
	}

	public void deleteIngredient(Integer ingredientId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Ingredient ingredients = (Ingredient) em.find(Ingredient.class, ingredientId);
		em.remove(ingredients);
		em.getTransaction().commit();
		em.close();
	}
}
