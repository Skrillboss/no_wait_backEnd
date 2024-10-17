package com.heredi.nowait.infrastructure.model.item.entity;

import com.heredi.nowait.infrastructure.model.queue.entity.QueueEntity;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador autogenerado

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String description;

    private Double rating; // Puede ser null

    @Column(nullable = false)
    private String mainImagePath;

    private String secondaryImagePath; // Puede ser null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "queue")
    private QueueEntity queue;

    public enum ItemStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        NO_STOCK,
        UNHANDLED_ERROR
    }
}
