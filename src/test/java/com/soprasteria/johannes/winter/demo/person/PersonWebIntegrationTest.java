package com.soprasteria.johannes.winter.demo.person;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.soprasteria.johannes.winter.demo.DemoWinterApplication;
import com.soprasteria.johannes.winter.demo.DemoWinterContext;
import com.soprasteria.johannes.winter.demo.SampleData;
import com.soprasteria.johannes.winter.framework.config.TestPropertySource;
import org.jsonbuddy.JsonArray;
import org.jsonbuddy.JsonObject;
import org.jsonbuddy.parse.JsonParser;
import org.junit.Test;

public class PersonWebIntegrationTest {

    @Test
    public void shouldRetrieveSavedPerson() throws IOException {
        DemoWinterApplication application = new DemoWinterApplication(0);
        TestPropertySource propertySource = new TestPropertySource();
        propertySource.add("demo.datasource.url", "jdbc:h2:mem:integrationTest;DB_CLOSE_DELAY=-1");
        application.setApplicationContext(new DemoWinterContext(propertySource));
        application.start();

        JsonObject personJson = new JsonObject()
                .put("givenName", SampleData.sampleGivenName())
                .put("familyName", SampleData.sampleFamilyName());

        URL url = new URL("http://localhost:" + application.getActualPort() + "/person");

        // TODO: Upstream JsonNode.toJson(URL) and JsonNode.toJson(HttpURLConnection)
        HttpURLConnection postConnection = (HttpURLConnection) url.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        PrintWriter printWriter = new PrintWriter(postConnection.getOutputStream());
        personJson.toJson(printWriter);
        printWriter.flush();
        int responseCode = postConnection.getResponseCode();
        assertThat(responseCode).isEqualTo(200);

        JsonObject response = JsonParser.parseToObject(postConnection);


        JsonObject fetched = JsonParser.parseToObject(new URL(url + "/" + response.requiredString("id")));
        assertThat(fetched.requiredString("givenName") + " " + fetched.requiredString("familyName"))
            .contains(personJson.requiredString("givenName") + " " + personJson.requiredString("familyName"));;

        // TODO: Upstream JsonParser.parseArray(URL) and JsonParser.parseArray(HttpURLConnection)
        HttpURLConnection getConnection = (HttpURLConnection) url.openConnection();
        assertThat(getConnection.getResponseCode()).isEqualTo(200);
        try (InputStream input = getConnection.getInputStream()) {
            JsonArray array = JsonParser.parseToArray(input);

            assertThat(array.objects(o -> o.requiredString("givenName") + " " + o.requiredString("familyName")))
                .contains(personJson.requiredString("givenName") + " " + personJson.requiredString("familyName"));
        }
    }

}
