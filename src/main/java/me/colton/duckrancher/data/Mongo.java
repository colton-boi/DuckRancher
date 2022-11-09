package me.colton.duckrancher.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


import static me.colton.duckrancher.SlimeRancher.instance;
import static org.bukkit.Bukkit.getLogger;

public class Mongo {

    private static MongoClient mongoClient;


    // Connect to the database
    private static void connect() {

        if (mongoClient != null) {
            mongoClient.close();
        }

        try {
            ConnectionString connectionString = new ConnectionString(instance.getConfig().getString("MongoDB"));
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            mongoClient = MongoClients.create(settings);
        } catch (Exception e) {
            getLogger().severe("Could not connect client!");
            e.printStackTrace();
        }

    }

    /**
     * Get a database from the current mongoClient
     *
     * @param name  the name of the database to find
     * @return      the database or null if one by the name could not be found
     */
    public static MongoDatabase getDatabase(String name) {

        if (mongoClient == null) {
            connect();
        }

        try {
            return mongoClient.getDatabase(name);
        } catch (Exception e) {
            getLogger().severe("Could not fetch database!");
            e.printStackTrace();
        }

        return null;
    }

}
