package com.example.domains.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -558914525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.example.domains.common.model.QBaseTimeEntity _super = new com.example.domains.common.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<com.example.domains.user.enums.Genre, EnumPath<com.example.domains.user.enums.Genre>> genres = this.<com.example.domains.user.enums.Genre, EnumPath<com.example.domains.user.enums.Genre>>createList("genres", com.example.domains.user.enums.Genre.class, EnumPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isVerified = createBoolean("isVerified");

    public final BooleanPath lawAgreement = createBoolean("lawAgreement");

    public final EnumPath<com.example.domains.user.enums.Level> level = createEnum("level", com.example.domains.user.enums.Level.class);

    public final StringPath nickname = createString("nickname");

    public final com.example.domains.user.enums.QOauthInfo oauthInfo;

    public final StringPath phoneNumber = createString("phoneNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final EnumPath<com.example.domains.user.enums.UserRole> userRole = createEnum("userRole", com.example.domains.user.enums.UserRole.class);

    public final EnumPath<com.example.domains.user.enums.UserState> userState = createEnum("userState", com.example.domains.user.enums.UserState.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.oauthInfo = inits.isInitialized("oauthInfo") ? new com.example.domains.user.enums.QOauthInfo(forProperty("oauthInfo")) : null;
    }

}

