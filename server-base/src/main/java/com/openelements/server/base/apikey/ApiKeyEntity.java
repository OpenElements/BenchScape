package com.openelements.server.base.apikey;

import com.openelements.server.base.tenantdata.AbstractEntityWithTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class ApiKeyEntity extends AbstractEntityWithTenant {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String user;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private LocalDateTime validUntil;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }
}
