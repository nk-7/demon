package dev.nk7.demon.api.v1;

import dev.nk7.demon.api.v1.dto.DependenciesReportDto;

/**
 * Demon backend API spec.
 */
public interface DemonApi {

  /**
   * Send dependencies report to demon backed.
   *
   * @param report Dependencies report.
   */
  void sendDependenciesReport(DependenciesReportDto report);

  /**
   * Base class for exception when interact with demon backend.
   */
  abstract class Exception extends RuntimeException {

    public Exception(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
