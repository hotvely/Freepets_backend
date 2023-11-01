package com.kh.Freepets.repo.member;

import com.kh.Freepets.domain.member.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface NotificationDAO extends JpaRepository<Notification, Integer>
{

    @Query(value = "SELECT * FROM NOTIFICATION WHERE ID=:id ORDER BY NOTI_CODE DESC", nativeQuery = true)
    List<Notification> showNotiByMember(String id);

    @Query(value = "SELECT * FROM NOTIFICATION WHERE NOTI_BOARDCODE=:boardCode AND NOTI_POSTCODE=:postCode", nativeQuery = true)
    Notification showBcodePcode(int boardCode, int postCode);

    @Query(value = "SELECT * FROM NOTIFICATION WHERER NOTI_BOARDCODE=:boardCode AND NOTI_POSTCODE=:postCode AND NOTI_PARENT_COMMENTCODE=:parentCode", nativeQuery = true)
    Notification showBoardPostParentCode(int boardCode, int postCode, int parentCode);

}
