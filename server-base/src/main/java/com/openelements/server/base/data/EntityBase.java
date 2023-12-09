package com.openelements.server.base.data;

import java.io.Serializable;
import java.util.UUID;

public interface EntityBase extends WithId, Serializable {

    default UUID id() {
        return getId();
    }

    UUID getId();

    void setId(UUID id);
}
