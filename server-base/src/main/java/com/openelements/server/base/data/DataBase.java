package com.openelements.server.base.data;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

public interface DataBase extends Serializable {

    UUID id();

    default Optional<UUID> getId() {
        return Optional.ofNullable(id());
    }

}
