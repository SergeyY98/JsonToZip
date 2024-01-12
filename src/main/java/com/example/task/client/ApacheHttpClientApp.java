package com.example.task.client;

import com.example.task.domain.Content;
import com.example.task.utils.CreateZipFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ApacheHttpClientApp {

  public Content[] invoke(String lastUpdateFrom, String lastUpdateTo) {

    try(CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
      client.start();

      final SimpleHttpRequest request =
          SimpleRequestBuilder
              .get()
              .setUri("https://bus.gov.ru/public-rest" +
                  "/api/7710568760-IndependentEvaluationAgencyinfoSecond")
              .addParameter("lastUpdateFrom", lastUpdateFrom)
              .addParameter("lastUpdateTo", lastUpdateTo)
              .addParameter("page", "0")
              .addParameter("size", "2000")
              .build();

      Future<SimpleHttpResponse> future =
          client.execute(request,
              new FutureCallback<SimpleHttpResponse>() {

                @Override
                public void completed(SimpleHttpResponse result) {
                  String response = result.getBodyText();
                  System.out.println("response::"+response);
                }

                @Override
                public void failed(Exception ex) {
                  System.out.println("response::"+ex);
                }

                @Override
                public void cancelled() {
                  // do nothing
                }

              });

      SimpleHttpResponse response = future.get();

      // Get HttpResponse Status
      System.out.println(response.getCode()); // 200
      System.out.println(response.getReasonPhrase()); // OK

      String body = response.getBodyText();
      ObjectMapper mapper = new ObjectMapper();
      JsonNode jsonNode = mapper.readTree(response.getBodyText());
      String sourceString = jsonNode.at("/pagedContent")
          .at("/content").toString();
      Content[] contents = mapper.readValue(sourceString, Content[].class);
      mapper.writeValue(new File("content.json"), body);

      new CreateZipFile();

      return contents;

    } catch (InterruptedException
        | ExecutionException
        | IOException e) {
      e.printStackTrace();
    }
    return new Content[0];
  }

}
