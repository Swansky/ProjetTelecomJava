package fr.zoltowski.core.db;

import java.util.List;

public interface Repository<T> {
    void saveOrUpdateInBatch(List<T> t);
}
