package dev.floraf.loader.misc.either;

public record Left<L, R>(L value) implements Either<L, R> {
    L unwrap() { return value; }
}