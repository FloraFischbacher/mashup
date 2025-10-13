package dev.floraf.loader.parsers.mpf.syntax;

public sealed interface Statement {}

record Use() implements Statement {}
record Mod() implements Statement {}
record Patch() implements Statement {}

