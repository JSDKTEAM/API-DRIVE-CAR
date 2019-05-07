package th.ac.ku.kps.eng.cpe.soa.driveCar.dao;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class SessionUtil {
    private static SessionUtil instance=new SessionUtil();
    private SessionFactory sessionFactory;
    
    private static Session currentSession;
    private static Transaction currentTransaction;
    
    public static SessionUtil getInstance(){
            return instance;
    }
    
    private SessionUtil(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
                
        sessionFactory = configuration.buildSessionFactory();
    }
    
    public static Session openCurrentSessionwithTransaction() {
        currentSession = getInstance().sessionFactory.openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
    
    public static void closeCurrentSession() {
        currentSession.close();
    }
     
    public static void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }
     
    public static Session getCurrentSession() {
        return currentSession;
    }
 
    public  void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
 
    public  static Transaction getCurrentTransaction() {
        return currentTransaction;
    }
 
    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
   
    public static Session getSession(){
		Session session =  getInstance().sessionFactory.openSession();
        return session;
    }

}
