package dev.floraf.loader.misc.either;

public record Right<L, R>(R value) implements Either<L, R> {
    R unwrap() { return value; }
}