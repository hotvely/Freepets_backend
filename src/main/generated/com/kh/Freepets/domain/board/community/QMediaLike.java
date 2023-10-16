package com.kh.Freepets.domain.board.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMediaLike is a Querydsl query type for MediaLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMediaLike extends EntityPathBase<MediaLike> {

    private static final long serialVersionUID = 1067720302L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMediaLike mediaLike = new QMediaLike("mediaLike");

    public final QMedia media;

    public final NumberPath<Integer> mediaLikeCode = createNumber("mediaLikeCode", Integer.class);

    public final com.kh.Freepets.domain.member.QMember member;

    public QMediaLike(String variable) {
        this(MediaLike.class, forVariable(variable), INITS);
    }

    public QMediaLike(Path<? extends MediaLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMediaLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMediaLike(PathMetadata metadata, PathInits inits) {
        this(MediaLike.class, metadata, inits);
    }

    public QMediaLike(Class<? extends MediaLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.media = inits.isInitialized("media") ? new QMedia(forProperty("media"), inits.get("media")) : null;
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

