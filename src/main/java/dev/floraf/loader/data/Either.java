package dev.floraf.loader.data;

/**
 * An implementation of the Either/Result type.
 * Used to represent a situation in which something may be one of two types.
 */
public sealed interface Either<L, R> { }

record Left<L, R>(L value) implements Either<L, R> {
    L unwrap() { return value; }
}

record Right<L, R>(R value) implements Either<L, R> {
    R unwrap() { return value; }
}