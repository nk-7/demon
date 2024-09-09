package dev.nk7.demon.gradle.visitor.git;

public class GitInfo {
  private final String branch;

  public GitInfo(String branch) {
    this.branch = branch;
  }

  public String getBranch() {
    return branch;
  }
}
