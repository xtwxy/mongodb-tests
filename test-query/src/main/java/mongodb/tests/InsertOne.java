package mongodb.tests;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;

public class InsertOne {

    public static void execute() {
        MongoClient client = Utils.getClient();
        MongoDatabase database = client.getDatabase("monitordb");
        MongoCollection<Document> collection = database.getCollection("history_ai");
        collection.drop();

        Date ts = new Date();

        Document document = new Document();
        document.append("id", "1");
        document.append("ts", ts);
        document.append("value", Math.random());

        collection.insertOne(document);

        client.close();
    }

}
