package mongodb.tests;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class QueryOneTest {
	@Test
	public void test() {
		MongoClient client = Utils.getClient();
		MongoDatabase database = client.getDatabase("monitordb");
		MongoCollection<Document> collection = database.getCollection("history_ai");

        FindIterable<Document> it = collection.find(and(eq("id", "1")));

        Document document = null;
        for(Document doc: it) {
            document = doc;
        }

        Assert.assertNotNull(document);

        client.close();
	}
	
	@Before
	public void setUp() {
		InsertOne.execute();
	}
	@After
	public void tearDown() {
		DeleteOne.execute();
	}
}
