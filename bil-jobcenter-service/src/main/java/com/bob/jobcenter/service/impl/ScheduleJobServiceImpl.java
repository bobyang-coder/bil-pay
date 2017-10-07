package com.bob.jobcenter.service.impl;

import com.bob.jobcenter.dao.ScheduleJobMapper;
import com.bob.jobcenter.model.ScheduleJob;
import com.bob.jobcenter.model.query.ScheduleJobQuery;
import com.bob.jobcenter.service.ScheduleJobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    public ScheduleJob getScheduleJobById(Long scheduleJobId) {
        return scheduleJobMapper.getById(scheduleJobId);
    }

    public boolean insertScheduleJob(ScheduleJob scheduleJob) {
        scheduleJobMapper.insert(scheduleJob);
        return true;
    }

    @Override
    public List<ScheduleJob> queryScheduleJob(ScheduleJobQuery scheduleJobQuery) {
        return scheduleJobMapper.listScheduleJob(scheduleJobQuery);

    }

    public void updateScheduleJob(ScheduleJob scheduleJob) {
        scheduleJobMapper.update(scheduleJob);
    }


    public void deleteScheduleJob(Long scheduleJobId) {
        scheduleJobMapper.delete(scheduleJobId);
    }


}
