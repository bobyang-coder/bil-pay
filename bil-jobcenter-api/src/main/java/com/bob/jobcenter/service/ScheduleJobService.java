

package com.bob.jobcenter.service;

import com.bob.jobcenter.model.ScheduleJob;
import com.bob.jobcenter.model.query.ScheduleJobQuery;

import java.util.List;

public interface ScheduleJobService {
    ScheduleJob getScheduleJobById(Long scheduleJobId);

    boolean insertScheduleJob(ScheduleJob scheduleJob);

    void updateScheduleJob(ScheduleJob scheduleJob);

    void deleteScheduleJob(Long scheduleJobId);

    List<ScheduleJob> queryScheduleJob(ScheduleJobQuery scheduleJobQuery);

}
