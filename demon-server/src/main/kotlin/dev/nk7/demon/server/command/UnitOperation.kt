package dev.nk7.demon.server.command

interface UnitOperation<T> {
  suspend fun execute(params: T)
}
