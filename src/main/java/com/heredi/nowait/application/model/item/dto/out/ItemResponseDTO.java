package com.heredi.nowait.application.model.item.dto.out;

import com.heredi.nowait.application.model.queue.dto.out.QueueResponseDTO;
import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ItemResponseDTO {

    private String id;
    private String name;
    private String description;
    private Double rating;
    private String mainImagePath;
    private String secondaryImagePath;
    private String status; // Representa el ItemStatus como String
    private List<ShiftResponseDTO> shifts;
    private QueueResponseDTO queueResponseDTO;
}
