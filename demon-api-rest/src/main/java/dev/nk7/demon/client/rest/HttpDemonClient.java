package dev.nk7.demon.client.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.nk7.demon.api.v1.DemonApi;
import dev.nk7.demon.api.v1.dto.ProjectReportDto;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class HttpDemonClient implements DemonApi {
  private final HttpClient httpClient = HttpClient.newHttpClient();
  private final ObjectMapper mapper;
  private final DemonApiUris demonApiUris;

  private HttpDemonClient(ObjectMapper mapper, DemonApiUris demonApiUris) {
    this.mapper = Objects.requireNonNull(mapper);
    this.demonApiUris = Objects.requireNonNull(demonApiUris);
  }

  public static HttpDemonClient fromApiUri(final String baseUri) {
    final ObjectMapper mapper = new ObjectMapper()
      .registerModule(new JavaTimeModule());
    final DemonApiUris demonApiUris = new DemonApiUris(baseUri);
    return new HttpDemonClient(mapper, demonApiUris);
  }

  @Override
  public void sendBuildReport(ProjectReportDto report) {
    final byte[] body = marshall(report);
    final HttpRequest request = HttpRequest.newBuilder(demonApiUris.buildUri)
      .POST(HttpRequest.BodyPublishers.ofByteArray(body))
      .header("Content-Type", "application/json")
      .build();

    try {
      httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    } catch (IOException e) {
      throw new ClientException("Cannot send report", e);
    } catch (InterruptedException e) {
      throw new ClientException("Sending build report was interrupted", e);
    }
  }

  private byte[] marshall(ProjectReportDto report) {
    try {
      return mapper.writeValueAsBytes(report);
    } catch (JsonProcessingException e) {
      throw new ClientException("Error when marshall report to JSON.", e);
    }
  }
}
