package dev.floraf.loader.misc.either;

/**
 * An implementation of the Either/Result type.
 * Used to represent a situation in which something may be one of two types.
 */
public sealed interface Either<L, R> permits Left, Right { }