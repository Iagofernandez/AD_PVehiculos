
package pvehiculos;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class Pvehiculos {
    
    // Creadas variables globales y static
    // Quizas necesitemos mas adelante poder acceder a ellas
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    public void conexionMongo(){
        // Realizando la conexionn a MongoDb y nuestra collection vendas
        // Deberia realizar variables globales o dejarlo así?
        
         mongoClient = new MongoClient("localhost", 27017);
         database = mongoClient.getDatabase("test");
        collection = database.getCollection("vendas");
        
        
    }
    
    public static void main(String[] args) {
       // Ejercicio DE EXAMEN
        // Se emplean oracle, OBJECTDB Y MONGO
        // Es necesario añadir los drivers de cada uno si no daran error
       
        
        
    }
    
}
