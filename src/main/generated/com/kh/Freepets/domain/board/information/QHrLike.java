package com.kh.Freepets.domain.board.information;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHrLike is a Querydsl query type for HrLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHrLike extends EntityPathBase<HrLike> {

    private static final long serialVersionUID = 866406187L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHrLike hrLike = new QHrLike("hrLike");

    public final QHospitalReview hospitalReview;

    public final NumberPath<Integer> hrLikeCode = createNumber("hrLikeCode", Integer.class);

    public final com.kh.Freepets.domain.member.QMember member;

    public QHrLike(String variable) {
        this(HrLike.class, forVariable(variable), INITS);
    }

    public QHrLike(Path<? extends HrLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHrLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHrLike(PathMetadata metadata, PathInits inits) {
        this(HrLike.class, metadata, inits);
    }

    public QHrLike(Class<? extends HrLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hospitalReview = inits.isInitialized("hospitalReview") ? new QHospitalReview(forProperty("hospitalReview"), inits.get("hospitalReview")) : null;
        this.member = inits.isInitialized("member") ? new com.kh.Freepets.domain.member.QMember(forProperty("member")) : null;
    }

}

