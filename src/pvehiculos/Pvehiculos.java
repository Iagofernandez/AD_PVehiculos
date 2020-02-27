
package pvehiculos;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
    */
    public static double id;
    public static String dni;
    public static String codveh;
    
   
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
            
        }
        
    }
    
    public static void main(String[] args) {
      Pvehiculos veh = new Pvehiculos();
      
      veh.conexionMongo();
      veh.mongoDocs();
      veh.desconexionMongo();
       
        
        
    }
    
}
