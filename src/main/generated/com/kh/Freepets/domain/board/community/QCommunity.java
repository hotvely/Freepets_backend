package com.kh.Freepets.domain.board.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunity is a Querydsl query type for Community
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunity extends EntityPathBase<Community> {

    private static final long serialVersionUID = 1742228668L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunity community = new QCommunity("community");

    public final StringPath commonAddFileUrl = createString("commonAddFileUrl");

    public final NumberPath<Integer> commonCode = createNumber("commonCode", Integer.class);

    public final DateTimePath<java.util.Date> commonDate = createDateTime("commonDate", java.util.Date.class);

    public final StringPath commonDesc = createString("commonDesc");

    public final StringPath commonTitle = createString("commonTitle");

    public final NumberPath<Integer> commonViews = createNumber("commonViews", Integer.class);

    public final com.kh.Freepets.domain.member.QMember member;

    public final StringPath youtubeUrl = createString("youtubeUrl");

    public QCommunity(String variable) {
        this(Community.class, forVariable(variable), INITS);
    }

    public QCommunity(Path<? extends Community> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunity(PathMetadata metadata, PathInits inits) {
        this(Community.class, metadata, inits);
    }

    public QCommunity(Class<? extends Community> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

