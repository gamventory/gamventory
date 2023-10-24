package com.gamventory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSerial is a Querydsl query type for Serial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSerial extends EntityPathBase<Serial> {

    private static final long serialVersionUID = -631101364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSerial serial = new QSerial("serial");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final QMember member;

    public final EnumPath<com.gamventory.constant.Platform> platform = createEnum("platform", com.gamventory.constant.Platform.class);

    public final StringPath serialNumber = createString("serialNumber");

    public final BooleanPath userStatus = createBoolean("userStatus");

    public QSerial(String variable) {
        this(Serial.class, forVariable(variable), INITS);
    }

    public QSerial(Path<? extends Serial> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSerial(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSerial(PathMetadata metadata, PathInits inits) {
        this(Serial.class, metadata, inits);
    }

    public QSerial(Class<? extends Serial> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

