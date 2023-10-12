package com.kh.Freepets.domain.board.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMediaComment is a Querydsl query type for MediaComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMediaComment extends EntityPathBase<MediaComment> {

    private static final long serialVersionUID = 704085032L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMediaComment mediaComment = new QMediaComment("mediaComment");

    public final StringPath mediaCommentAddFileUrl = createString("mediaCommentAddFileUrl");

    public final NumberPath<Integer> mediaCommentCode = createNumber("mediaCommentCode", Integer.class);

    public final StringPath mediaCommentCodeSuper = createString("mediaCommentCodeSuper");

    public final DateTimePath<java.util.Date> mediaCommentDate = createDateTime("mediaCommentDate", java.util.Date.class);

    public final StringPath mediaCommentDesc = createString("mediaCommentDesc");

    public final com.kh.Freepets.domain.member.QMember member;

    public final QMedia midea;

    public QMediaComment(String variable) {
        this(MediaComment.class, forVariable(variable), INITS);
    }

    public QMediaComment(Path<? extends MediaComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMediaComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMediaComment(PathMetadata metadata, PathInits inits) {
        this(MediaComment.class, metadata, inits);
    }

    public QMediaComment(Class<? extends MediaComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
        this.midea = inits.isInitialized("midea") ? new QMedia(forProperty("midea"), inits.get("midea")) : null;
    }

}

