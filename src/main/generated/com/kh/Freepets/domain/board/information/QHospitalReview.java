package com.kh.Freepets.domain.board.information;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHospitalReview is a Querydsl query type for HospitalReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHospitalReview extends EntityPathBase<HospitalReview> {

    private static final long serialVersionUID = -826116196L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHospitalReview hospitalReview = new QHospitalReview("hospitalReview");

    public final StringPath hospitalAddress = createString("hospitalAddress");

    public final StringPath hospitalName = createString("hospitalName");

    public final NumberPath<Integer> hospitalReviewCode = createNumber("hospitalReviewCode", Integer.class);

    public final NumberPath<Integer> hospitalReviewCommentCount = createNumber("hospitalReviewCommentCount", Integer.class);

    public final DateTimePath<java.util.Date> hospitalReviewDate = createDateTime("hospitalReviewDate", java.util.Date.class);

    public final StringPath hospitalReviewDesc = createString("hospitalReviewDesc");

    public final StringPath hospitalReviewFileUrl = createString("hospitalReviewFileUrl");

    public final NumberPath<Integer> hospitalReviewLike = createNumber("hospitalReviewLike", Integer.class);

    public final ComparablePath<Character> hospitalReviewReportYn = createComparable("hospitalReviewReportYn", Character.class);

    public final StringPath hospitalReviewTitle = createString("hospitalReviewTitle");

    public final StringPath hospitalReviewUrl = createString("hospitalReviewUrl");

    public final NumberPath<Integer> hospitalReviewViews = createNumber("hospitalReviewViews", Integer.class);

    public final com.kh.Freepets.domain.member.QMember member;

    public QHospitalReview(String variable) {
        this(HospitalReview.class, forVariable(variable), INITS);
    }

    public QHospitalReview(Path<? extends HospitalReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHospitalReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHospitalReview(PathMetadata metadata, PathInits inits) {
        this(HospitalReview.class, metadata, inits);
    }

    public QHospitalReview(Class<? extends HospitalReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

