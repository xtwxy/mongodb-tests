package mongodb.tests;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class DeleteOne {

    public static long execute() {
        MongoClient client = Utils.getClient();
        MongoDatabase database = client.getDatabase("monitordb");
        MongoCollection<Document> collection = database.getCollection("history_ai");

        DeleteResult result = collection.deleteOne(and(eq("id", "1")));

        client.close();
        return result.getDeletedCount();
    }

}
