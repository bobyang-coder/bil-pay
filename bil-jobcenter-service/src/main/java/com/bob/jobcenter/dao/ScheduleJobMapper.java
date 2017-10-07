package com.bob.jobcenter.dao;

import com.bob.jobcenter.model.ScheduleJob;

import java.util.List;

public interface ScheduleJobMapper {
    ScheduleJob getById(Long id);

    int delete(Long id);

    int insert(ScheduleJob record);

    int update(ScheduleJob record);

    List<ScheduleJob> listScheduleJob(ScheduleJob record);
}