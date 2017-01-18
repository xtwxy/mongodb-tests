package mongodb.tests;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.mongodb.client.model.Filters.*;

public class QueryByRangeTest {
    @Test
    public void test() {
        MongoClient client = Utils.getClient();
        MongoDatabase database = client.getDatabase("monitordb");
        MongoCollection<Document> collection = database.getCollection("history_ai");

        DateTime dateTime = DateTime.now();
        DateTime begin = dateTime.minus(Duration.standardDays(20));
        DateTime end = dateTime.minus(Duration.standardDays(10));

        Set<Integer> ids = new HashSet<>();
        ids.add(2);
        ids.add(3);
        ids.add(5);
        ids.add(7);
        ids.add(11);
        ids.add(13);

        long ts1 = System.currentTimeMillis();
        System.out.println(begin.toDate());
        System.out.println(end.toDate());

        FindIterable<Document> it = collection.find(and(eq("id", 1), gt("ts", begin.toDate()), lt("ts", end.toDate())));

        long count = 0;
        for (Document doc : it) {
            count += 1;
        }
        long ts2 = System.currentTimeMillis();

        System.out.println("seconds elapsed: " + (ts2 - ts1) / 1000.0);
        Assert.assertTrue(count > 0);
        System.out.println("count = " + count);

        client.close();
    }

    @Before
    public void setUp() {
        InsertMany.execute();
    }

    @After
    public void tearDown() {
        DropCollection.execute();
    }
}
