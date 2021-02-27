package Package;
import Managers.*;
import java.io.IOException;
import java.text.ParseException;

public class Main {

	 public static void main(String[] args) throws IOException, ParseException {

		 Managers.ManageCustomer.start();
		 Managers.ManageIngredient.start();
		 Managers.ManageOrders.start();
		 Managers.ManagePizza.start();
		 Managers.ManageIngredientPizza.start();
		 Managers.ManageOrderDetails.start();
		 
     }
	
}
