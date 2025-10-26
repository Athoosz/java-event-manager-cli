package com.athoosz.utils;

import java.util.Objects;
import java.util.function.Consumer;

public class ValueUtils {
  public static <T> void setValueIfNotNull(T value, Consumer<T> setter) {
    if (value instanceof String) {
      if (Objects.nonNull(value) && !((String) value).isBlank()) {
        setter.accept(value);
      }
    } else if (Objects.nonNull(value)) {
      setter.accept(value);
    }
  }
}
