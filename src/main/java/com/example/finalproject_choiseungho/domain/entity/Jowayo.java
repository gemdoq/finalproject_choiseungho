package com.example.finalproject_choiseungho.domain.entity;

import com.example.finalproject_choiseungho.domain.dto.JowayoDto;
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
@SQLDelete(sql = "UPDATE jowayo SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
public class Jowayo extends JowayoBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime deletedAt;

    public static Jowayo toJowayo(Post post, User user) {
        return Jowayo.builder()
                .post(post)
                .user(user)
                .build();
    }

    public JowayoDto toJowayoDto() {
        return JowayoDto.builder()
                .id(this.id)
                .post(this.post)
                .user(this.user)
                .createdAt(this.getCreatedAt())
                .build();
    }
}