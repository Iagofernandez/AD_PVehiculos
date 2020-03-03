
package pvehiculos;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.bson.Document;
 // Ejercicio DE EXAMEN
        // Se emplean oracle, OBJECTDB Y MONGO
        // Es necesario añadir los drivers de cada uno si no daran error

public class Pvehiculos {
    
  /*
    VARIABLES DE CONEXION MONGO
    */
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static FindIterable<Document> datosMongo;
    
    /*
    VARIABLES CAMPO DE MONGO
    Tres Variables tipo normales
    Todas static, asi solo se crea una vez para evitar confusión
    */
    public static double id;
    public static String dni;
    public static String codveh;
    
    /*
    VARIABLE CREADAS PARA OBJECTDB
    UN Objeto de TIPO Clientes
    */
    static Clientes clienteDat;
    static String nomec;
    static int comprasCli;
    static Vehiculos vehiculosDat;
    static String nomveh;
    static int prezoorixe;
    static int anomatricula;
    static int pf;
    
    
   
    public void conexionMongo(){
        // Realizando la conexionn a MongoDb y nuestra collection vendas
        // Hay que iniciar una terminal con mongoDB
        
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("test");
        collection = database.getCollection("vendas");
        
        
    }
    public void desconexionMongo(){
        mongoClient.close();
    }
    public void mongoDocs() throws SQLException{
        datosMongo = collection.find();
        
        /*
        Retorno de varios doc, se necesita un iterator
        */
        for (Document doc : datosMongo){
            id = doc.getDouble("_id");
            dni = doc.getString("dni");
            codveh = doc.getString("codveh");
            System.out.println("id " + id);
            System.out.println("dni " + dni);
            System.out.println("codveh " + codveh);
        
            
    // Ahora accedemos a objectDB??
            /*
            Creamos un EntityManagerFactory
            Como es un metodo perteneciente a la clase Persistence y es no es static
            Se hace referencia directamente a la clase a la que pertenece
            De parametro necesita la unidad Persistence a la que se quiere acceder
            */
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("/home/oracle/objectdb-2.7.5_01/db/vehicli.odb");
            EntityManager em = emf.createEntityManager();
            
            //Sacamso el nombre y compras de los Clientes de la clase Clientes.class
            
            /* 
            Uso de la interfaz Transiction
            Begin nos permite comenzarla
            */
            em.getTransaction().begin();
            TypedQuery<Clientes> query = em.createQuery("select p from Clientes p where p.dni = :value", Clientes.class);
            clienteDat = query.setParameter("value", dni).getSingleResult();
            
            // Esto nos permite almacenar nome y compras de un cliente
            nomec = clienteDat.getNomec();
            comprasCli = clienteDat.getNcompras();
            
            em.getTransaction().commit();
            
            /*
            Ahora se realizara lo mismo pero con las class Vehiculos
            */
            
            em.getTransaction().begin();
            TypedQuery<Vehiculos> query2 = em.createQuery("select v from Vehiculos v where v.codveh= :value2", Vehiculos.class);
            vehiculosDat = query2.setParameter("value2", codveh).getSingleResult();
            
            nomveh = vehiculosDat.getNomveh();
            prezoorixe = vehiculosDat.getPrezoorixe();
            anomatricula = vehiculosDat.getAnomatricula();
            
            em.getTransaction().commit();
            /*
            Cerramos conexion con todos los objetod Entity creados ya que no necesitamos sacar mas datos de nuestras bases de datos
            A partir de aqui ya tenemos que introducir los datos de nuestras variables que sacamos de ObjectDb
            Iniciando la conexion con oracle
            */
            
            em.close();
            emf.close();
            
            if(comprasCli>0){
                pf = prezoorixe-((2019-anomatricula)*500)-500;
            }else{
                pf = prezoorixe -((2019-anomatricula)*500);
            }
            System.out.println("Pf " + pf);
    /*
            Comenzando con la conexión a ORACLE
            Necesario iniciar una terminal con Oracle
           
            */
    Connection connection;        
    String driver = "jdbc:oracle:thin:";
    String host = "localhost.localdomain";
    String porto = "1521";
    String sid = "orcl";
    String usuario = "hr";
    String password = "hr";
    String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;
    
    connection = DriverManager.getConnection(url);
    
    /*
    Para insertar usando ? es mejor el PreparedStatement
    */
    PreparedStatement pst = connection.prepareStatement("insert into finalveh values(?,?,?,(tipo_vehf(?,?)))");
    // Aqui es donde se realizan las inserrciones, se usa un sistema de "indices"
    pst.setDouble(1,id);
    pst.setString(2,dni);
    pst.setString(3,nomec);
    pst.setString(4,nomveh);
    pst.setInt(5, pf);
    
    pst.executeUpdate();
    connection.close();
        }
        
}
    
    public static void main(String[] args) throws SQLException {
      Pvehiculos veh = new Pvehiculos();
      
      veh.conexionMongo();
      veh.mongoDocs();
      veh.desconexionMongo();
       
        
        
    }
    
}
