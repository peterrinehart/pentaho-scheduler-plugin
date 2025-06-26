package com.pentaho.appshell.scheduler.api;

import com.pentaho.appshell.scheduler.model.ApiV1SchedulerJobPostRequest;

import javax.ws.rs.core.Response;
import java.util.List;

public class SchedulerResource implements ISchedulerApi {
  @Override
  public Response apiV1SchedulerBlockoutAddPost( ApiV1SchedulerJobPostRequest apiV1SchedulerJobPostRequest ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerBlockoutBlockoutjobsGet() {
    return null;
  }

  @Override
  public Response apiV1SchedulerBlockoutBlockstatusPost( ApiV1SchedulerJobPostRequest apiV1SchedulerJobPostRequest ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerBlockoutHasblockoutsGet() {
    return null;
  }

  @Override
  public Response apiV1SchedulerBlockoutShouldFireNowGet() {
    return null;
  }

  @Override
  public Response apiV1SchedulerBlockoutWillFirePost( ApiV1SchedulerJobPostRequest apiV1SchedulerJobPostRequest ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerCanExecuteSchedulesGet() {
    return null;
  }

  @Override
  public Response apiV1SchedulerCanScheduleGet() {
    return null;
  }

  @Override
  public Response apiV1SchedulerGeneratedContentForScheduleGet( String lineageId ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerGetContentCleanerJobGet() {
    return null;
  }

  @Override
  public Response apiV1SchedulerGetJobsGet() {
    return null;
  }

  @Override
  public Response apiV1SchedulerIsScheduleAllowedGet( String id ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerJobPost( ApiV1SchedulerJobPostRequest apiV1SchedulerJobPostRequest ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerJobStatePost( String body ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerJobinfoGet( String jobId, String asCronString ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerPauseJobPost( String body ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerPausePost() {
    return null;
  }

  @Override
  public Response apiV1SchedulerRemoveJobPut( String body ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerRemoveJobsPost( List<String> requestBody ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerResumeJobPost( String body ) {
    return null;
  }

  @Override
  public Response apiV1SchedulerShutdownPost() {
    return null;
  }

  @Override
  public Response apiV1SchedulerStartPost() {
    return null;
  }

  @Override
  public Response apiV1SchedulerStateGet() {
    return null;
  }

  @Override
  public Response apiV1SchedulerTriggerNowPost( String body ) {
    return null;
  }
}
