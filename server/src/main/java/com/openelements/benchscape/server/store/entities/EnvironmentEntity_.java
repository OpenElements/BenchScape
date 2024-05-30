package com.openelements.benchscape.server.store.entities;

import com.openelements.benchscape.server.store.data.OperationSystem;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;

@StaticMetamodel(EnvironmentEntity.class)
public class EnvironmentEntity_ {
    public static volatile SingularAttribute<EnvironmentEntity, UUID> id;
    public static volatile SingularAttribute<EnvironmentEntity, String> name;
    public static volatile SingularAttribute<EnvironmentEntity, String> gitOriginUrl;
    public static volatile SingularAttribute<EnvironmentEntity, String> gitBranch;
    public static volatile SingularAttribute<EnvironmentEntity, String> systemArch;
    public static volatile SingularAttribute<EnvironmentEntity, Integer> systemProcessors;
    public static volatile SingularAttribute<EnvironmentEntity, OperationSystem> osFamily;
    public static volatile SingularAttribute<EnvironmentEntity, String> osName;
    public static volatile SingularAttribute<EnvironmentEntity, String> osVersion;
    public static volatile SingularAttribute<EnvironmentEntity, String> jvmVersion;
    public static volatile SingularAttribute<EnvironmentEntity, String> jvmName;
    public static volatile SingularAttribute<EnvironmentEntity, String> jmhVersion;
}
