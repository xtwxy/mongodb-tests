package mongodb.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.joda.time.DateTime;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class QueryOne {
	public static int ID_COUNT = 1;
	public static int COMMIT_COUNT = 5000;

	public static void main(String[] args) {
		switch (args.length) {
		case 1:
			ID_COUNT = Integer.parseInt(args[0]);
			break;
		default:
			break;
		}
		MongoClient client = new MongoClient("192.168.0.68", 27017);
		MongoDatabase testDB = client.getDatabase("monitordb");
		MongoCollection<Document> collection = testDB.getCollection("history_ai");
		collection.drop();

		long rows = 0;

		// insert many
		List<Document> documents = new ArrayList<>();
		int commitCount = 0;
		long ts1 = System.currentTimeMillis();
		for (int i = 0; i < ID_COUNT; ++i) {
			DateTime stop = DateTime.now();
			DateTime start = stop.minusDays(365 * 3);
			System.out.println(start);
			do {

				Date ts = new Date(start.getMillis());
				start = start.plusMinutes(1);

				Document document = new Document();
				document.append("id", "1");
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

}
