package com.openelements.server.base.apikey;

import com.openelements.server.base.tenantdata.AbstractEntityWithTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class ApiKeyEntity extends AbstractEntityWithTenant {

    @Column(nullable = false, name = "api_key_name")
    private String name;

    @Column(nullable = false, name = "api_key_user")
    private String user;

    @Column(nullable = false, name = "api_key_key_hash")
    private String keyHash;

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

    public void setUser(final String user) {
        this.user = user;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }
}
