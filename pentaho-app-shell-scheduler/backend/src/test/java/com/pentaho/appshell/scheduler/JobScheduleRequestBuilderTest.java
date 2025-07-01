package com.pentaho.appshell.scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.pentaho.platform.api.scheduler2.SimpleJobTrigger;
import org.pentaho.platform.web.http.api.resources.ComplexJobTriggerProxy;
import org.pentaho.platform.web.http.api.resources.JobScheduleRequest;

import com.pentaho.appshell.scheduler.model.*;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import com.pentaho.appshell.scheduler.model.BaseJob.UiPassParamEnum;

class JobScheduleRequestBuilderTest {
  private JobScheduleRequestBuilder builder;

  @BeforeEach
  void setUp() {
    builder = new JobScheduleRequestBuilder();
  }

  @Test
  void testCreateJobSchedulerRequest_RunOnce() {
    RunOnceJobScheduleRequest job = new RunOnceJobScheduleRequest();
    setBaseJobFields( job, UiPassParamEnum.RUN_ONCE );
    JobScheduleRequest req = builder.createJobSchedulerRequest( job );
    assertNotNull( req.getSimpleJobTrigger() );
    assertNull( req.getComplexJobTrigger() );
  }

  @Test
  void testCreateJobSchedulerRequest_Seconds() {
    SecondsJobScheduleRequest job = new SecondsJobScheduleRequest();
    setBaseJobFields( job, UiPassParamEnum.SECONDS );
    job.setRepeatInterval( 5 );
    JobScheduleRequest req = builder.createJobSchedulerRequest( job );
    assertNotNull( req.getSimpleJobTrigger() );
    assertNull( req.getComplexJobTrigger() );
  }

  @Test
  void testCreateJobSchedulerRequest_Daily() {
    DailyJobScheduleRequest job = new DailyJobScheduleRequest();
    setBaseJobFields( job, UiPassParamEnum.DAILY );
    job.setIsEveryNDays( true );
    job.setRepeatInterval( 2 );
    job.setIgnoreDST( false );
    JobScheduleRequest req = builder.createJobSchedulerRequest( job );
    assertNull( req.getSimpleJobTrigger() );
    assertNotNull( req.getComplexJobTrigger() );
  }

  @Test
  void testCreateJobSchedulerRequest_Weekly() {
    WeeklyJobScheduleRequest job = new WeeklyJobScheduleRequest();
    setBaseJobFields( job, UiPassParamEnum.WEEKLY );
    job.setDaysOfWeek( Arrays.asList(
      WeeklyJobScheduleRequest.DaysOfWeekEnum.MONDAY,
      WeeklyJobScheduleRequest.DaysOfWeekEnum.FRIDAY ) );
    JobScheduleRequest req = builder.createJobSchedulerRequest( job );
    assertNull( req.getSimpleJobTrigger() );
    assertNotNull( req.getComplexJobTrigger() );
  }

  @Test
  void testCreateJobSchedulerRequest_Monthly() {
    MonthlyJobScheduleRequest job = new MonthlyJobScheduleRequest();
    setBaseJobFields( job, UiPassParamEnum.MONTHLY );
    job.setDayOfMonth( 15 );
    job.setWeekOfMonth( MonthlyJobScheduleRequest.WeekOfMonthEnum.FIRST );
    job.setDayOfWeek( MonthlyJobScheduleRequest.DayOfWeekEnum.MONDAY );
    JobScheduleRequest req = builder.createJobSchedulerRequest( job );
    assertNull( req.getSimpleJobTrigger() );
    assertNotNull( req.getComplexJobTrigger() );
  }

  @Test
  void testCreateJobSchedulerRequest_Yearly() {
    YearlyJobScheduleRequest job = new YearlyJobScheduleRequest();
    setBaseJobFields( job, UiPassParamEnum.YEARLY );
    job.setDayOfMonth( 1 );
    job.setWeekOfMonth( YearlyJobScheduleRequest.WeekOfMonthEnum.SECOND );
    job.setDayOfWeek( YearlyJobScheduleRequest.DayOfWeekEnum.TUESDAY );
    JobScheduleRequest req = builder.createJobSchedulerRequest( job );
    assertNull( req.getSimpleJobTrigger() );
    assertNotNull( req.getComplexJobTrigger() );
  }

  @Test
  void testCreateJobSchedulerRequest_Cron() {
    CronJobScheduleRequest job = new CronJobScheduleRequest();
    setBaseJobFields( job, UiPassParamEnum.CRON );
    job.setCronExpression( "0 0 12 * * ?" );
    Exception ex = assertThrows( IllegalArgumentException.class, () -> builder.createJobSchedulerRequest( job ) );
    assertTrue( ex.getMessage().contains( "Unsupported UI pass param" ) );
  }

  @Test
  void testCreateSimpleJobTrigger_RunOnce() {
    RunOnceJobScheduleRequest req = new RunOnceJobScheduleRequest();
    req.setStartDate( new Date() );
    SimpleJobTrigger trigger = builder.createSimpleJobTrigger( req );
    assertNotNull( trigger );
  }

  @Test
  void testCreateSimpleJobTrigger_Seconds() {
    SecondsJobScheduleRequest req = new SecondsJobScheduleRequest();
    req.setStartDate( new Date() );
    req.setRepeatInterval( 10 );
    SimpleJobTrigger trigger = builder.createSimpleJobTrigger( req );
    assertNotNull( trigger );
  }

  @Test
  void testCreateSimpleJobTrigger_Unsupported() {
    Exception ex = assertThrows( IllegalArgumentException.class, () -> builder.createSimpleJobTrigger( "bad" ) );
    assertTrue( ex.getMessage().contains( "Unsupported schedule request type" ) );
  }

  @Test
  void testSetJobTriggerStartTime_SimpleJobTrigger() {
    BaseJob job = new RunOnceJobScheduleRequest();
    setBaseJobFields( job, UiPassParamEnum.RUN_ONCE );
    SimpleJobTrigger trigger = Mockito.mock( SimpleJobTrigger.class );
    builder.setJobTriggerStartTime( job, trigger );
    Mockito.verify( trigger ).setStartTime( Mockito.any() );
  }

  @Test
  void testSetJobTriggerStartTime_ComplexJobTriggerProxy() {
    BaseJob job = new RunOnceJobScheduleRequest();
    setBaseJobFields( job, UiPassParamEnum.RUN_ONCE );
    ComplexJobTriggerProxy trigger = Mockito.mock( ComplexJobTriggerProxy.class );
    builder.setJobTriggerStartTime( job, trigger );
    Mockito.verify( trigger ).setStartTime( Mockito.any() );
  }

  @Test
  void testGetComplexTriggerFromWeekly() {
    WeeklyJobScheduleRequest req = new WeeklyJobScheduleRequest();
    setBaseJobFields( req, UiPassParamEnum.WEEKLY );
    req.setDaysOfWeek( Arrays.asList(
      WeeklyJobScheduleRequest.DaysOfWeekEnum.MONDAY,
      WeeklyJobScheduleRequest.DaysOfWeekEnum.FRIDAY ) );
    ComplexJobTriggerProxy proxy = builder.getComplexTriggerFromWeekly( req );
    assertNotNull( proxy );
  }

  @Test
  void testGetCompletxTriggerFromMonthly() {
    MonthlyJobScheduleRequest req = new MonthlyJobScheduleRequest();
    setBaseJobFields( req, UiPassParamEnum.MONTHLY );
    req.setDayOfMonth( 10 );
    req.setWeekOfMonth( MonthlyJobScheduleRequest.WeekOfMonthEnum.FIRST );
    req.setDayOfWeek( MonthlyJobScheduleRequest.DayOfWeekEnum.MONDAY );
    ComplexJobTriggerProxy proxy = builder.getCompletxTriggerFromMonthly( req );
    assertNotNull( proxy );
  }

  @Test
  void testGetComplexTriggerFromYearly() {
    YearlyJobScheduleRequest req = new YearlyJobScheduleRequest();
    setBaseJobFields( req, UiPassParamEnum.YEARLY );
    req.setDayOfMonth( 1 );
    req.setWeekOfMonth( YearlyJobScheduleRequest.WeekOfMonthEnum.SECOND );
    req.setDayOfWeek( YearlyJobScheduleRequest.DayOfWeekEnum.TUESDAY );
    ComplexJobTriggerProxy proxy = builder.getComplexTriggerFromYearly( req );
    assertNotNull( proxy );
  }

  // Helper to set required fields
  private void setBaseJobFields( BaseJob job, BaseJob.UiPassParamEnum param ) {
    job.setJobId( "jobId" );
    job.setJobName( "jobName" );
    job.setJobParams( Collections.emptyList() );
    job.setParameters( Collections.emptyList() );
    job.setInputFile( "input.ktr" );
    job.setOutputFile( "output.log" );
    job.setTimeZone( "UTC" );
    job.setEnableSafeMode( Boolean.TRUE );
    job.setGatheringMetrics( Boolean.FALSE );
    job.setLogLevel( "Basic" );
    job.setUiPassParam( param );
    job.setStartDate( new Date() );
  }
}
