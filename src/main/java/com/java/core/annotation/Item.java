package com.java.core.annotation;

import java.util.Objects;

/**
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/3/16 15:42
 */
public class Item {
    private String description;
    private int partNumber;

    public Item(String description, int partNumber) {
        this.description = description;
        this.partNumber = partNumber;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", partNumber=" + partNumber +
                '}';
    }

    @Override
    @LogEntry("global")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return partNumber == item.partNumber &&
                Objects.equals(description, item.description);
    }

    @Override
    @LogEntry("global")
    public int hashCode() {
        return Objects.hash(description, partNumber);
    }
}
