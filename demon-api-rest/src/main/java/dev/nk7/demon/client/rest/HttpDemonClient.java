package dev.nk7.demon.client.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.nk7.demon.api.v1.DemonApi;
import dev.nk7.demon.api.v1.dto.ProjectReportDto;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HttpDemonClient implements DemonApi {

  private static final Logger log = LoggerFactory.getLogger(HttpDemonClient.class);
  private final OkHttpClient client;
  private final ObjectMapper mapper;
  private final String apiUrl;

  private HttpDemonClient(OkHttpClient client, ObjectMapper mapper, String apiUrl) {
    this.client = Objects.requireNonNull(client);
    this.mapper = Objects.requireNonNull(mapper);
    this.apiUrl = Objects.requireNonNull(apiUrl);
  }

  public static HttpDemonClient fromApiUrl(final String apiUrl) {
    final ObjectMapper mapper = new ObjectMapper()
      .registerModule(new JavaTimeModule());
    final OkHttpClient client = new OkHttpClient();
    return new HttpDemonClient(client, mapper, apiUrl);
  }

  @Override
  public void sendBuildReport(ProjectReportDto report) {
    final String body = marshall(report);
    final Request request = new Request.Builder()
      .url(apiUrl + "/build")
      .post(RequestBody.create(body, MediaType.parse("application/json")))
      .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new BadResponseCodeException(response);
      }
    } catch (IOException exception) {
      throw new ClientException("Cannot send report", exception);
    }
  }

  private String marshall(ProjectReportDto report) {
    try {
      return mapper.writeValueAsString(report);
    } catch (JsonProcessingException e) {
      throw new ClientException("Error when marshall report to JSON.", e);
    }
  }
}
