package dev.nk7.demon.gradle.visitor.git;

import dev.nk7.demon.gradle.visitor.Visitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;

public class GitInfoVisitor implements Visitor<Project, GitInfo> {
  @Override
  public GitInfo visit(Project target) {
    final File dir = target.getProjectDir();
    try (final Repository repository = new FileRepositoryBuilder().setGitDir(new File(dir, ".git")).build()) {
      return new GitInfo(repository.getBranch());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
