package mongodb.tests;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertMany {
    public static final long ID_COUNT = 20000;
    public static final Duration DURATION = Duration.standardDays(30);

    public static int COMMIT_COUNT = 5000;

    public static void execute() {
        MongoClient client = Utils.getClient();
        MongoDatabase database = client.getDatabase("monitordb");
        MongoCollection<Document> collection = database.getCollection("history_ai");
        collection.drop();

        long rows = 0;

        // insert many
        List<Document> documents = new ArrayList<>();
        int commitCount = 0;
        long ts1 = System.currentTimeMillis();
        for (int i = 0; i < ID_COUNT; ++i) {
            DateTime stop = DateTime.now();
            DateTime start = stop.minus(DURATION);

            System.out.println(start);
            do {

                Date ts = new Date(start.getMillis());
                start = start.plusMinutes(1);

                Document document = new Document();
                document.append("id", i);
                document.append("ts", ts);
                document.append("value", Math.random());
                documents.add(document);

                rows++;
                commitCount++;
                if (commitCount >= COMMIT_COUNT) {
                    collection.insertMany(documents);
                    documents = new ArrayList<>();
                    commitCount = 0;
                }
            } while (start.isBefore(stop.getMillis()));
        }
        if (!documents.isEmpty()) {
            collection.insertMany(documents);
            documents = new ArrayList<>();
        }
        long ts2 = System.currentTimeMillis();
        System.out.println("rows/second: " + rows / ((ts2 - ts1) / 1000.0));

        client.close();
    }

    public static void main(String[] args) {
        execute();
    }
}
