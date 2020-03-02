
package pvehiculos;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
    static String nomeCli;
    static int comprasCli;
    static Vehiculos vehiculosDat;
    static String nomveh;
    static int prezoorixe;
    static int anomatricula;
    
    
   
    public void conexionMongo(){
        // Realizando la conexionn a MongoDb y nuestra collection vendas
        
        
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("test");
        collection = database.getCollection("vendas");
        
        
    }
    public void desconexionMongo(){
        mongoClient.close();
    }
    public void mongoDocs(){
        datosMongo = collection.find();
        
        /*
        Retorno de varios doc, se necesita un iterator
        */
        for (Document doc : datosMongo){
            id = doc.getDouble("_id");
            dni = doc.getString("dni");
            codveh = doc.getString("codveh");
            
            
    // Ahora accedemos a objectDB??
            /*
            Creamos un EntityManagerFactory
            Como es un metodo perteneciente a la clase Persistence y es no es static
            Se hace referencia directamente a la clase a la que pertenece
            De parametro necesita la unidad Persistence a la que se quiere acceder
            */
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/vehicli.odb");
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
            nomeCli = clienteDat.getNomec();
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
            
            
        }
        
    }
    
    public static void main(String[] args) {
      Pvehiculos veh = new Pvehiculos();
      
      veh.conexionMongo();
      veh.mongoDocs();
      veh.desconexionMongo();
       
        
        
    }
    
}
