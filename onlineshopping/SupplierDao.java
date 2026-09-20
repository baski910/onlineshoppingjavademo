import com.example.model.Product;
import com.example.model.Supplier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;

public class SupplierDao {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Supplier supplier = new Supplier();
            supplier.setName("TechCorp Supplies");
            supplier.setEmail("contact@techcorp.com");

            Product p1 = new Product();
            p1.setName("Mechanical Keyboard");
            p1.setPrice(new BigDecimal("79.99"));

            Product p2 = new Product();
            p2.setName("Wireless Mouse");
            p2.setPrice(new BigDecimal("29.99"));

            // Maintain bidirectional relationship safely
            supplier.addProduct(p1);
            supplier.addProduct(p2);

            // CascadeType.ALL saves products automatically
            session.persist(supplier);

            tx.commit();
            System.out.println("Supplier and products saved successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}

