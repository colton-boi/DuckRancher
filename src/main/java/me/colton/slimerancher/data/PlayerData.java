package me.colton.slimerancher.data;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static me.colton.slimerancher.data.Mongo.getDatabase;

public class PlayerData {

    private static HashMap<UUID, HashMap<String, Object>> playerData = new HashMap<>();

    /**
     * Get the hashmap of ALL player data
     * @return          HashMap of player data
     */
    public static HashMap<UUID, HashMap<String, Object>> getPlayerData() {
        return playerData;
    }

    /**
     * Get a hashmap of a player's data
     * @param player    the player to get data of
     * @return          the data (HashMap of Strings and Objects)
     */
    public static HashMap<String, Object> getPlayerData(UUID player) {
        return playerData.get(player);
    }

    /**
     * Replace the playerData variable with a new HashMap
     * @param map       the HashMap to replace current playerData with
     */
    public static void setPlayerData(HashMap<UUID, HashMap<String, Object>> map) {
        playerData = map;
    }

    /**
     * Update a player's data with new information
     * @param player    the player to update
     * @param map       the new HashMap of data
     */
    public static void setPlayerData(UUID player, HashMap<String, Object> map) {
        if (playerData.containsKey(player)) {
            playerData.replace(player, map);
        } else {
            playerData.put(player, map);
        }
    }

    /**
     * Load the data of player from MongoDB
     * @param player    UUID of player to load
     */
    public static void loadPlayerData(UUID player) {

        MongoCollection<Document> collection = Objects.requireNonNull(getDatabase("SlimeRancher")).getCollection("playerData");

        MongoIterable<Document> findIterable = collection.find(new Document("_id", player));
        if (findIterable.first() != null && findIterable.first().get("data") != null) {
            setPlayerData(player, (HashMap<String, Object>) findIterable.first().get("data"));
        } else {
            HashMap<String, Object> map = defaultPlayerData();
            setPlayerData(player, map);
        }

    }

    /**
     * Saves the player data of ALL ONLINE PLAYERS (In the event of a crash or restart)
     */
    public static void savePlayerData() {

        getPlayerData().forEach((uuid, stringObjectHashMap) -> {
            Document document = new Document("_id", uuid);
            document.put("data", stringObjectHashMap);

            MongoCollection<Document> collection = Objects.requireNonNull(getDatabase("SlimeRancher")).getCollection("playerData");
            if (collection.find(new BasicDBObject("_id", uuid)).first() != null) {
                collection.findOneAndReplace(new BasicDBObject("_id", uuid), document);
            } else {
                collection.insertOne(document);
            }
        });

    }

    /**
     * Save the player data of one player
     * @param player    the player to save data of
     */
    public static void savePlayerData(UUID player) {

        Document document = new Document("_id", player);
        document.put("data", getPlayerData(player));

        MongoCollection<Document> collection = Objects.requireNonNull(getDatabase("SlimeRancher")).getCollection("playerData");
        if (collection.find(new BasicDBObject("_id", player)).first() != null) {
            collection.findOneAndReplace(new BasicDBObject("_id", player), document);
        } else {
            collection.insertOne(document);
        }

    }

    /**
     * Get the default player data for new players
     * @return      HashMap with default information
     */
    private static HashMap<String, Object> defaultPlayerData() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("Coins", 250);
        map.put("Energy", 100);
        map.put("MaxEnergy", 100);
        map.put("Health", 100);
        map.put("MaxHealth", 100);

        map.put("Entities", new HashMap<>());

        return map;
    }
}
