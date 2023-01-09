package com.example.finalproject_choiseungho.domain.entity;

import com.example.finalproject_choiseungho.domain.dto.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@SQLDelete(sql = "UPDATE alarm SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
public class Alarm extends AlarmBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private AlarmType alarmType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private String text;

    private LocalDateTime deletedAt;
}