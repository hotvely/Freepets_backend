package com.kh.Freepets.domain.board.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLostLike is a Querydsl query type for LostLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLostLike extends EntityPathBase<LostLike> {

    private static final long serialVersionUID = -1122814552L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLostLike lostLike = new QLostLike("lostLike");

    public final QLost lost;

    public final NumberPath<Integer> lostLikeCode = createNumber("lostLikeCode", Integer.class);

    public final com.kh.Freepets.domain.member.QMember member;

    public QLostLike(String variable) {
        this(LostLike.class, forVariable(variable), INITS);
    }

    public QLostLike(Path<? extends LostLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLostLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLostLike(PathMetadata metadata, PathInits inits) {
        this(LostLike.class, metadata, inits);
    }

    public QLostLike(Class<? extends LostLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lost = inits.isInitialized("lost") ? new QLost(forProperty("lost"), inits.get("lost")) : null;
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

