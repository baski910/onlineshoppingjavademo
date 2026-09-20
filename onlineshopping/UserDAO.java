import com.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDAO {

    public User validateLogin(String email, String password) {
        Transaction transaction = null;
        User user = null;
        
        // Open a session using your Hibernate Util class
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // HQL syntax using named parameters to avoid SQL Injection
            String hql = "FROM User WHERE email = :email AND password = :password";
            Query<User> query = session.createQuery(hql, User.class);
            
            // Bind variables safely
            query.setParameter("email", email);
            query.setParameter("password", password); // Note: In production, hash this password!
            
            // Fetch result safely
            user = query.uniqueResult();
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }
}
