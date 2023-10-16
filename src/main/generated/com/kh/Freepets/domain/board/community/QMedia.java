package com.kh.Freepets.domain.board.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMedia is a Querydsl query type for Media
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMedia extends EntityPathBase<Media> {

    private static final long serialVersionUID = 1243606583L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMedia media = new QMedia("media");

    public final StringPath category = createString("category");

    public final StringPath mediaAddFileUrl = createString("mediaAddFileUrl");

    public final NumberPath<Integer> mediaCode = createNumber("mediaCode", Integer.class);

    public final DateTimePath<java.util.Date> mediaDate = createDateTime("mediaDate", java.util.Date.class);

    public final StringPath mediaDesc = createString("mediaDesc");

    public final StringPath mediaPhoto = createString("mediaPhoto");

    public final StringPath mediaTitle = createString("mediaTitle");

    public final NumberPath<Integer> mediaViews = createNumber("mediaViews", Integer.class);

    public final com.kh.Freepets.domain.member.QMember member;

    public final StringPath youtubeUrl = createString("youtubeUrl");

    public QMedia(String variable) {
        this(Media.class, forVariable(variable), INITS);
    }

    public QMedia(Path<? extends Media> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMedia(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMedia(PathMetadata metadata, PathInits inits) {
        this(Media.class, metadata, inits);
    }

    public QMedia(Class<? extends Media> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

