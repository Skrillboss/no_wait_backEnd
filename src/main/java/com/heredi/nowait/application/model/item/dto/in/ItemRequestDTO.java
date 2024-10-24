package com.heredi.nowait.application.model.item.dto.in;

import com.heredi.nowait.application.model.queue.dto.in.QueueRequestDTO;
import lombok.Data;

@Data
public class ItemRequestDTO {

    private String name;
    private String description;
    private int peoplePerShift;
    private String mainImagePath;
    private String secondaryImagePath;
    private String status;
    private QueueRequestDTO queueRequestDTO;
}
