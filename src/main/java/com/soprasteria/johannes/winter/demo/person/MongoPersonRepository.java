package com.soprasteria.johannes.winter.demo.person;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoPersonRepository implements PersonRepository {

    private MongoCollection<Document> collection;

    public MongoPersonRepository(MongoDatabase database) {
        collection = database.getCollection("person");
    }

    @Override
    public Person retrieve(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        return mapToPerson(collection.find(query).first());
    }


    @Override
    public List<Person> listAll() {
        return collection.find().map(this::mapToPerson).into(new ArrayList<>());
    }

    public Person mapToPerson(Document doc) {
        Person person = new Person();
        person.setFamilyName(doc.getString("familyName"));
        person.setGivenName(doc.getString("givenName"));
        person.setDateOfBirth(doc.getDate("dateOfBirth"));
        person.setId(doc.get("_id").toString());
        return person;
    }

    @Override
    public Person create(Person person) {
        Document document = new Document()
                .append("familyName", person.getFamilyName())
                .append("givenName", person.getGivenName())
                .append("dateOfBirth", person.getDateOfBirth())
                ;
        collection.insertOne(document);
        person.setId(document.getObjectId("_id").toString());

        return person;
    }

}
