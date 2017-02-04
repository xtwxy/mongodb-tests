package mongodb.tests;

import com.mongodb.MongoClient;

/**
 * Created by master on 1/18/17.
 */
public class Utils {
    public static final String HOST = "192.168.0.174";
    public static final int PORT = 27017;

    public static MongoClient getClient() {
        return new MongoClient(HOST, PORT);
    }

}
