package com.soprasteria.johannes.winter.demo.cats;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoCatRepository implements CatRepository {

    private MongoCollection<Document> collection;

    public MongoCatRepository(MongoDatabase database) {
        collection = database.getCollection("cats");
    }

    @Override
    public List<Cat> listAll() {
        return collection.find().map(this::mapToCat).into(new ArrayList<>());
    }

    @Override
    public Cat retrieve(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        return mapToCat(collection.find(query).first());
    }

    private Cat mapToCat(Document doc) {
        if (doc == null) {
            return null;
        }
        Cat cat = new Cat();
        cat.setId(doc.get("_id").toString());
        cat.setName(doc.getString("name"));
        cat.setDateOfBirth(doc.getDate("dateOfBirth"));
        return cat;
    }

    @Override
    public void create(Cat cat) {
        Document doc = new Document();
        doc.put("name", cat.getName());
        doc.put("dateOfBirth", cat.getDateOfBirth());

        collection.insertOne(doc);
        cat.setId(doc.get("_id").toString());
    }

}
