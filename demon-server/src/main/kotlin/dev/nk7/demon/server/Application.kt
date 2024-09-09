package dev.nk7.demon.server

import io.micronaut.runtime.Micronaut

object Application {
  @JvmStatic
  fun main(args: Array<String>) {
    Micronaut.run(Application.javaClass, *args)
  }
}
