package com.heredi.nowait.domain.item.model;

import com.heredi.nowait.domain.business.model.Business;
import com.heredi.nowait.domain.queue.model.Queue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Business business;
    private Queue queue;

    public enum ItemStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        NO_STOCK,
        UNHANDLED_ERROR
    }
}
