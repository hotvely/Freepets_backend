package com.kh.Freepets.domain.board.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLost is a Querydsl query type for Lost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLost extends EntityPathBase<Lost> {

    private static final long serialVersionUID = -236998031L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLost lost = new QLost("lost");

    public final StringPath lostAddFileUrl = createString("lostAddFileUrl");

    public final NumberPath<Integer> lostCode = createNumber("lostCode", Integer.class);

    public final NumberPath<Integer> lostCommentCount = createNumber("lostCommentCount", Integer.class);

    public final DateTimePath<java.util.Date> lostDate = createDateTime("lostDate", java.util.Date.class);

    public final StringPath lostDesc = createString("lostDesc");

    public final NumberPath<Integer> lostLike = createNumber("lostLike", Integer.class);

    public final ComparablePath<Character> lostReportYN = createComparable("lostReportYN", Character.class);

    public final StringPath lostTitle = createString("lostTitle");

    public final StringPath lostUrl = createString("lostUrl");

    public final NumberPath<Integer> lostViews = createNumber("lostViews", Integer.class);

    public final com.kh.Freepets.domain.member.QMember member;

    public QLost(String variable) {
        this(Lost.class, forVariable(variable), INITS);
    }

    public QLost(Path<? extends Lost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLost(PathMetadata metadata, PathInits inits) {
        this(Lost.class, metadata, inits);
    }

    public QLost(Class<? extends Lost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

