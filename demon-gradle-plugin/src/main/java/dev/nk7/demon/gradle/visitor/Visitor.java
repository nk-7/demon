package dev.nk7.demon.gradle.visitor;

import java.util.function.Function;

public interface Visitor<T, R> extends Function<T, R> {

  R visit(T target);

  @Override
  default R apply(T t) {
    return visit(t);
  }
}
