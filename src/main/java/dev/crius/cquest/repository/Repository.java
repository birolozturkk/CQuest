package dev.crius.cquest.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import dev.crius.cquest.SortedList;
import dev.crius.cquest.DatabaseObject;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Repository<T extends DatabaseObject, S> {
    private final SortedList<T> entries;
    private final Dao<T, S> dao;
    private final Comparator<T> comparator;

    private final ConnectionSource connectionSource;

    public Repository(ConnectionSource connectionSource, Class<T> clazz, Comparator<T> comparator) throws SQLException {
        this.connectionSource = connectionSource;
        this.comparator = comparator;
        this.entries = new SortedList<>(comparator);
        TableUtils.createTableIfNotExists(connectionSource, clazz);
        this.dao = DaoManager.createDao(connectionSource, clazz);
        this.dao.setAutoCommit(getDatabaseConnection(), false);
        this.entries.addAll(dao.queryForAll());
        this.entries.forEach(t -> t.setChanged(false));
        sort();
    }

    public void sort() {
        getEntries().sort(comparator);
    }

    public CompletableFuture<Void> save(T value) {
        return CompletableFuture.runAsync(() -> {
            try {
                if (!value.isChanged()) return;
                dao.createOrUpdate(value);
                dao.commit(getDatabaseConnection());
                value.setChanged(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }


    public void save() {
        try {
            List<T> entryList = new ArrayList<>(entries);
            for (T value : entryList) {
                if (!value.isChanged()) continue;
                dao.createOrUpdate(value);
                value.setChanged(false);
            }
            dao.commit(getDatabaseConnection());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addEntry(T t) {
        entries.add(t);
    }

    public List<T> getEntries() {
        return entries;
    }

    public Optional<T> getEntry(T entry) {
        return entries.getEntry(entry);
    }

    public CompletableFuture<Void> delete(T t) {
        entries.remove(t);
        return CompletableFuture.runAsync(() -> {
            try {
                dao.delete(t);
                dao.commit(getDatabaseConnection());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> delete(Collection<T> t) {
        entries.removeAll(t);
        return CompletableFuture.runAsync(() -> {
            try {
                dao.delete(t);
                dao.commit(getDatabaseConnection());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private DatabaseConnection getDatabaseConnection() throws SQLException {
        return connectionSource.getReadWriteConnection(null);
    }

    public Dao<T, S> getDao() {
        return dao;
    }
}
