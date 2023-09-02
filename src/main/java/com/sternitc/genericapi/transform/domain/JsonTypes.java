package com.sternitc.genericapi.transform.domain;

public enum JsonTypes {
    String,
    Number_Integer,
    Number_Double,
    Number_Float,
    Boolean,
    Object,
    Array;

    public static Class<?> getClassName(JsonTypes type) {
        switch (type) {
            case String -> {
                return String.class;
            }
            case Number_Integer -> {
                return Integer.class;
            }
            case Number_Double -> {
                return Double.class;
            }
            case Number_Float -> {
                return Float.class;
            }
            case Boolean -> {
                return Boolean.class;
            }
            default -> throw new RuntimeException("Not yet implemented");
        }
    }
}
