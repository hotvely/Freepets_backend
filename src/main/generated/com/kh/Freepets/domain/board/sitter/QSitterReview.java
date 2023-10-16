package com.kh.Freepets.domain.board.sitter;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSitterReview is a Querydsl query type for SitterReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSitterReview extends EntityPathBase<SitterReview> {

    private static final long serialVersionUID = -1543329250L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSitterReview sitterReview = new QSitterReview("sitterReview");

    public final QSitter sitter;

    public final NumberPath<Integer> sitterReviewCode = createNumber("sitterReviewCode", Integer.class);

    public final StringPath sitterReviewDesc = createString("sitterReviewDesc");

    public final NumberPath<Integer> sitterReviewRatings = createNumber("sitterReviewRatings", Integer.class);

    public QSitterReview(String variable) {
        this(SitterReview.class, forVariable(variable), INITS);
    }

    public QSitterReview(Path<? extends SitterReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSitterReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSitterReview(PathMetadata metadata, PathInits inits) {
        this(SitterReview.class, metadata, inits);
    }

    public QSitterReview(Class<? extends SitterReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sitter = inits.isInitialized("sitter") ? new QSitter(forProperty("sitter"), inits.get("sitter")) : null;
    }

}

