package com.kh.Freepets.domain.board.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLostComment is a Querydsl query type for LostComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLostComment extends EntityPathBase<LostComment> {

    private static final long serialVersionUID = 213344942L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLostComment lostComment = new QLostComment("lostComment");

    public final StringPath lCcommentImg = createString("lCcommentImg");

    public final NumberPath<Integer> lCommentCode = createNumber("lCommentCode", Integer.class);

    public final NumberPath<Integer> lCommentCodeSuper = createNumber("lCommentCodeSuper", Integer.class);

    public final DateTimePath<java.util.Date> lCommentDate = createDateTime("lCommentDate", java.util.Date.class);

    public final StringPath lCommentDesc = createString("lCommentDesc");

    public final ComparablePath<Character> lCommentReportYN = createComparable("lCommentReportYN", Character.class);

    public final QLost lost;

    public final com.kh.Freepets.domain.member.QMember member;

    public QLostComment(String variable) {
        this(LostComment.class, forVariable(variable), INITS);
    }

    public QLostComment(Path<? extends LostComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLostComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLostComment(PathMetadata metadata, PathInits inits) {
        this(LostComment.class, metadata, inits);
    }

    public QLostComment(Class<? extends LostComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lost = inits.isInitialized("lost") ? new QLost(forProperty("lost"), inits.get("lost")) : null;
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

