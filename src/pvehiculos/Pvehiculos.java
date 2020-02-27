
package pvehiculos;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class Pvehiculos {
    public void conexionMongo(){
        // Realizando la conexionn a MongoDb y nuestra collection vendas
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("vendas");
        
        
    }
    
    public static void main(String[] args) {
       // Ejercicio DE EXAMEN
        // Se emplean oracle, OBJECTDB Y MONGO
        // Es necesario añadir los drivers de cada uno si no daran error
        // Realizando las conexiones a Mongo y la base de datos
        
        
    }
    
}
