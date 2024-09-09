package dev.nk7.demon.api.v1;

import dev.nk7.demon.api.v1.dto.ProjectReportDto;

public interface DemonApi {

  void sendBuildReport(ProjectReportDto report);

  abstract class Exception extends RuntimeException {
    public Exception(String message) {
      super(message);
    }

    public Exception(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
