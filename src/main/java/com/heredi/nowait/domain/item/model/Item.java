package com.heredi.nowait.domain.item.model;

import com.heredi.nowait.domain.queue.model.Queue;
import com.heredi.nowait.domain.shift.model.Shift;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String mainImagePath;
    private String secondaryImagePath;
    private ItemStatus status;
    private Queue queue;

    public enum ItemStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        NO_STOCK,
        UNHANDLED_ERROR
    }
}
