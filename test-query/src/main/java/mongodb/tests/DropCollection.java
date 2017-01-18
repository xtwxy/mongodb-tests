package mongodb.tests;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DropCollection {

    public static void execute() {
        MongoClient client = Utils.getClient();
        MongoDatabase database = client.getDatabase("monitordb");
        MongoCollection<Document> collection = database.getCollection("history_ai");
        collection.drop();

        client.close();
    }

}
