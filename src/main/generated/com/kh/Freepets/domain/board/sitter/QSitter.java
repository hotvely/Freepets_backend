package com.kh.Freepets.domain.board.sitter;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSitter is a Querydsl query type for Sitter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSitter extends EntityPathBase<Sitter> {

    private static final long serialVersionUID = -1946535130L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSitter sitter = new QSitter("sitter");

    public final com.kh.Freepets.domain.member.QMember member;

    public final NumberPath<Integer> sitterCode = createNumber("sitterCode", Integer.class);

    public final StringPath sitterDesc = createString("sitterDesc");

    public final StringPath sitterImg = createString("sitterImg");

    public final StringPath sitterLoc = createString("sitterLoc");

    public final NumberPath<Integer> sitterPrice = createNumber("sitterPrice", Integer.class);

    public final NumberPath<Double> sitterRatings = createNumber("sitterRatings", Double.class);

    public final StringPath sitterTitle = createString("sitterTitle");

    public QSitter(String variable) {
        this(Sitter.class, forVariable(variable), INITS);
    }

    public QSitter(Path<? extends Sitter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSitter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSitter(PathMetadata metadata, PathInits inits) {
        this(Sitter.class, metadata, inits);
    }

    public QSitter(Class<? extends Sitter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

