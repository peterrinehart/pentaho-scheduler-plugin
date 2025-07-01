package com.pentaho.appshell.scheduler.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import com.pentaho.appshell.scheduler.model.BaseJob;
import com.pentaho.appshell.scheduler.model.SetSchedulerStateRequest;

// Add these imports:
import com.pentaho.appshell.scheduler.JobScheduleRequestBuilder;
import org.pentaho.platform.web.http.api.resources.JobScheduleRequest;
import org.pentaho.platform.web.http.api.resources.services.ISchedulerServicePlugin;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.api.scheduler2.SchedulerException;
import java.io.IOException;

public class SchedulerResource implements ISchedulerApi {

  // Scheduler service instance
  private final ISchedulerServicePlugin schedulerService;

  public SchedulerResource() {
    this.schedulerService = PentahoSystem.get( ISchedulerServicePlugin.class, "ISchedulerService2", null );
  }

  @Override
  public Response blockoutsExist() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'blockoutsExist'" );
  }

  @Override
  public Response canUserExecuteSchedules() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'canUserExecuteSchedules'" );
  }

  @Override
  public Response canUserScheduleAnyContent() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'canUserScheduleAnyContent'" );
  }

  @Override
  public Response createBlockoutJob( @Valid @NotNull BaseJob baseJob ) {
    try {
      JobScheduleRequestBuilder builder = new JobScheduleRequestBuilder();
      JobScheduleRequest jobScheduleRequest = builder.createJobSchedulerRequest( baseJob );
      String jobId = schedulerService.addBlockout( jobScheduleRequest ).getJobId();
      return Response.ok( jobId ).build();
    } catch ( Exception e ) {
      return Response.serverError().entity( e.getMessage() ).build();
    }
  }

  @Override
  public Response createJobSchedule( @Valid @NotNull BaseJob baseJob ) {
    try {
      JobScheduleRequestBuilder builder = new JobScheduleRequestBuilder();
      JobScheduleRequest jobScheduleRequest = builder.createJobSchedulerRequest( baseJob );
      String jobId = schedulerService.createJob( jobScheduleRequest ).getJobId();
      return Response.ok( jobId ).build();
    } catch ( Exception e ) {
      return Response.serverError().entity( e.getMessage() ).build();
    }
  }

  @Override
  public Response deleteMultipleScheduledJobs( @Valid @NotNull List<String> requestBody ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'deleteMultipleScheduledJobs'" );
  }

  @Override
  public Response deleteScheduledJobById( String jobId ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'deleteScheduledJobById'" );
  }

  @Override
  public Response getAllBlockoutJobs() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'getAllBlockoutJobs'" );
  }

  @Override
  public Response getAllScheduledJobs() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'getAllScheduledJobs'" );
  }

  @Override
  public Response getBlockoutStatus( @Valid @NotNull BaseJob baseJob ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'getBlockoutStatus'" );
  }

  @Override
  public Response getContentCleanerJob() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'getContentCleanerJob'" );
  }

  @Override
  public Response getGeneratedContentByJobId( String jobId ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'getGeneratedContentByJobId'" );
  }

  @Override
  public Response getScheduledJobById( String jobId, String asCronString ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'getScheduledJobById'" );
  }

  @Override
  public Response getScheduledJobState( String jobId ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'getScheduledJobState'" );
  }

  @Override
  public Response getSchedulerState() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'getSchedulerState'" );
  }

  @Override
  public Response isScheduleAllowedForFile( @NotNull String id ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'isScheduleAllowedForFile'" );
  }

  @Override
  public Response pauseScheduledJob( String jobId ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'pauseScheduledJob'" );
  }

  @Override
  public Response resumeScheduledJob( String jobId ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'resumeScheduledJob'" );
  }

  @Override
  public Response setSchedulerState( @Valid @NotNull SetSchedulerStateRequest setSchedulerStateRequest ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'setSchedulerState'" );
  }

  @Override
  public Response shouldBlockoutFireNow() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'shouldBlockoutFireNow'" );
  }

  @Override
  public Response triggerScheduledJob( String jobId, @Valid @NotNull String body ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'triggerScheduledJob'" );
  }

  @Override
  public Response updateScheduledJob( String jobId, @Valid @NotNull BaseJob baseJob ) {
    try {
      JobScheduleRequestBuilder builder = new JobScheduleRequestBuilder();
      JobScheduleRequest jobScheduleRequest = builder.createJobSchedulerRequest( baseJob );
      jobScheduleRequest.setJobId( jobId );
      String updatedJobId = schedulerService.updateJob( jobScheduleRequest ).getJobId();
      return Response.ok( updatedJobId ).build();
    } catch ( Exception e ) {
      return Response.serverError().entity( e.getMessage() ).build();
    }
  }

  @Override
  public Response willBlockoutFire( @Valid @NotNull BaseJob baseJob ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException( "Unimplemented method 'willBlockoutFire'" );
  }

}
