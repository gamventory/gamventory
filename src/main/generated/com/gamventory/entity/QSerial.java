package com.gamventory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSerial is a Querydsl query type for Serial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSerial extends EntityPathBase<Serial> {

    private static final long serialVersionUID = -631101364L;

    public static final QSerial serial = new QSerial("serial");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final EnumPath<com.gamventory.constant.Platform> platform = createEnum("platform", com.gamventory.constant.Platform.class);

    public final StringPath serialNumber = createString("serialNumber");

    public QSerial(String variable) {
        super(Serial.class, forVariable(variable));
    }

    public QSerial(Path<? extends Serial> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSerial(PathMetadata metadata) {
        super(Serial.class, metadata);
    }

}

