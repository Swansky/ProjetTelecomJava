package fr.zoltowski.core.files;

import java.util.Optional;

public interface ConsumeCsv<T> {
    Optional<T> fromCsv(String[] lines);
}
