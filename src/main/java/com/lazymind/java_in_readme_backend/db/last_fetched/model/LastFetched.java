package com.lazymind.java_in_readme_backend.db.last_fetched.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "last_fetched")
public class LastFetched {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "formatted_timestamp")
    private String formattedTimestamp;

}
