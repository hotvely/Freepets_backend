package com.kh.Freepets.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 429618254L;

    public static final QMember member = new QMember("member1");

    public final StringPath address = createString("address");

    public final StringPath authority = createString("authority");

    public final DateTimePath<java.util.Date> birth = createDateTime("birth", java.util.Date.class);

    public final DateTimePath<java.util.Date> createAccountDate = createDateTime("createAccountDate", java.util.Date.class);

    public final ComparablePath<Character> deleteAccountYN = createComparable("deleteAccountYN", Character.class);

    public final StringPath email = createString("email");

    public final ComparablePath<Character> gender = createComparable("gender", Character.class);

    public final StringPath id = createString("id");

    public final StringPath memberImg = createString("memberImg");

    public final StringPath memberInfo = createString("memberInfo");

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

