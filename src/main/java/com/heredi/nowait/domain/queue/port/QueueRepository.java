package com.heredi.nowait.domain.queue.port;

import com.heredi.nowait.domain.queue.model.Queue;
import com.heredi.nowait.domain.shift.model.Shift;

public interface QueueRepository {
    void save(Queue queue);
    Queue getQueueById(Long queueId);
}
