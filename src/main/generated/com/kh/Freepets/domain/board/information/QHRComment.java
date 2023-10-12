package com.kh.Freepets.domain.board.information;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHRComment is a Querydsl query type for HRComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHRComment extends EntityPathBase<HRComment> {

    private static final long serialVersionUID = -805725589L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHRComment hRComment = new QHRComment("hRComment");

    public final QHospitalReview hospitalReview;

    public final NumberPath<Integer> hrCommentCode = createNumber("hrCommentCode", Integer.class);

    public final DateTimePath<java.util.Date> hrCommentDate = createDateTime("hrCommentDate", java.util.Date.class);

    public final StringPath hrCommentDesc = createString("hrCommentDesc");

    public final StringPath hrCommentImg = createString("hrCommentImg");

    public final ComparablePath<Character> hrCommentReportYn = createComparable("hrCommentReportYn", Character.class);

    public final com.kh.Freepets.domain.member.QMember member;

    public final NumberPath<Integer> superHrCommentCode = createNumber("superHrCommentCode", Integer.class);

    public QHRComment(String variable) {
        this(HRComment.class, forVariable(variable), INITS);
    }

    public QHRComment(Path<? extends HRComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHRComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHRComment(PathMetadata metadata, PathInits inits) {
        this(HRComment.class, metadata, inits);
    }

    public QHRComment(Class<? extends HRComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hospitalReview = inits.isInitialized("hospitalReview") ? new QHospitalReview(forProperty("hospitalReview"), inits.get("hospitalReview")) : null;
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

