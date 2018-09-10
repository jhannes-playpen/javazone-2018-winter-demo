package com.soprasteria.johannes.winter.demo.person;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoPersonRepository implements PersonRepository {

    private MongoCollection<Document> collection;

    public MongoPersonRepository(MongoDatabase database) {
        collection = database.getCollection("person");
    }

    @Override
    public List<Person> listAll() {
        return collection.find().map(doc -> {
            Person person = new Person();
            person.setFamilyName(doc.getString("familyName"));
            person.setGivenName(doc.getString("givenName"));
            person.setId(doc.get("_id").toString());
            return person;
        }).into(new ArrayList<>());
    }

    @Override
    public Person create(Person person) {
        Document document = new Document()
                .append("familyName", person.getFamilyName())
                .append("givenName", person.getGivenName())
                ;
        collection.insertOne(document);
        person.setId(document.getObjectId("_id").toString());

        return person;
    }

}
