package com.git.company.service.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
@Builder

public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "name")
    private String name;

    @Column(name = "about")
    private String about;

    @Column(name = "location")
    private String location;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;


}
