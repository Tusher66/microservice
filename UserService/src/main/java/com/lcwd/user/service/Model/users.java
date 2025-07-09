package com.lcwd.user.service.Model;

import com.lcwd.user.service.Data.ResData.Rating;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Builder

public class users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "about")
    private String about;

    @Column(name = "insert_by")
    private Long insertBy;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    @Transient
    private List<Rating> ratings = new ArrayList<>();

}
