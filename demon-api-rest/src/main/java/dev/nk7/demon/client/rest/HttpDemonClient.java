package dev.nk7.demon.client.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.nk7.demon.api.v1.DemonApi;
import dev.nk7.demon.api.v1.dto.DependenciesReportDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class HttpDemonClient implements DemonApi {
  private static final Logger log = LoggerFactory.getLogger(HttpDemonClient.class);
  private final ObjectMapper mapper;
  private final DemonApiUrls demonApiUris;

  private HttpDemonClient(ObjectMapper mapper, DemonApiUrls demonApiUris) {
    this.mapper = Objects.requireNonNull(mapper);
    this.demonApiUris = Objects.requireNonNull(demonApiUris);
  }

  public static HttpDemonClient fromApiUri(final String baseUri) throws MalformedURLException {
    final ObjectMapper mapper = new ObjectMapper()
      .registerModule(new JavaTimeModule());
    final DemonApiUrls demonApiUris = new DemonApiUrls(baseUri);
    return new HttpDemonClient(mapper, demonApiUris);
  }

  @Override
  public void sendDependenciesReport(DependenciesReportDto report) {
    final byte[] body = marshall(report);
    final HttpURLConnection connection = openConnection(demonApiUris.buildUrl);
    try {
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);
      connection.setDoInput(true);
      try (OutputStream os = connection.getOutputStream()) {
        os.write(body, 0, body.length);
        os.flush();
      }
    } catch (IOException e) {
      throw new ClientException("Cannot send report", e);
    }
    try (
      final InputStream is = connection.getInputStream();
      final InputStreamReader isr = new InputStreamReader(is);
      final BufferedReader reader = new BufferedReader(isr)) {
      final StringBuilder sb = new StringBuilder();
      reader.lines().forEach(line -> sb.append(line).append(System.lineSeparator()));
      final String response = sb.toString();
      log.debug("Got response '{}'.", response);
    } catch (IOException e) {
      throw new ClientException("Cannot send report", e);
    }
  }

  private HttpURLConnection openConnection(URL url) {
    try {
      return (HttpURLConnection) url.openConnection();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private byte[] marshall(DependenciesReportDto report) {
    try {
      return mapper.writeValueAsBytes(report);
    } catch (JsonProcessingException e) {
      throw new ClientException("Error when marshall report to JSON.", e);
    }
  }
}
