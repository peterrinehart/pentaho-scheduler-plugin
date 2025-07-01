package com.pentaho.appshell.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.pentaho.platform.api.scheduler2.SimpleJobTrigger;
import org.pentaho.platform.web.http.api.resources.ComplexJobTriggerProxy;
import org.pentaho.platform.web.http.api.resources.JobScheduleParam;
import org.pentaho.platform.web.http.api.resources.JobScheduleRequest;
import org.pentaho.gwt.widgets.client.utils.TimeUtil;
import org.pentaho.platform.api.scheduler2.ComplexJobTrigger;
import org.pentaho.platform.api.scheduler2.IJobScheduleParam;
import org.pentaho.platform.api.scheduler2.JobTrigger;

import com.pentaho.appshell.scheduler.model.BaseJob;
import com.pentaho.appshell.scheduler.model.CronJobScheduleRequest;
import com.pentaho.appshell.scheduler.model.DailyJobScheduleRequest;
import com.pentaho.appshell.scheduler.model.JobParam;
import com.pentaho.appshell.scheduler.model.MonthlyJobScheduleRequest;
import com.pentaho.appshell.scheduler.model.RunOnceJobScheduleRequest;
import com.pentaho.appshell.scheduler.model.SecondsJobScheduleRequest;
import com.pentaho.appshell.scheduler.model.WeeklyJobScheduleRequest;
import com.pentaho.appshell.scheduler.model.YearlyJobScheduleRequest;

public class JobScheduleRequestBuilder {

  public JobScheduleRequest createJobSchedulerRequest( BaseJob baseJob ) {
    JobScheduleRequest jobScheduleRequest = new JobScheduleRequest();
    jobScheduleRequest.setJobId( baseJob.getJobId() );
    jobScheduleRequest.setJobName( baseJob.getJobName() );

    // copy the JobParams to the JobScheduleRequest
    List<IJobScheduleParam> jobParams = new ArrayList<>();
    for ( JobParam p : baseJob.getJobParams() ) {
      JobScheduleParam jobScheduleParam = new JobScheduleParam( p.getName(), p.getValue() );
      jobScheduleParam.setType( p.getType() );
      jobParams.add( jobScheduleParam );
    }
    jobScheduleRequest.setJobParameters( jobParams );

    Map<String, String> jobProperties = new HashMap<>();
    for ( JobParam p : baseJob.getParameters() ) {
      jobProperties.put( p.getName(), p.getValue() );
    }
    jobScheduleRequest.setPdiParameters( jobProperties );

    jobScheduleRequest.setInputFile( baseJob.getInputFile() );
    jobScheduleRequest.setOutputFile( baseJob.getOutputFile() );
    jobScheduleRequest.setTimeZone( baseJob.getTimeZone() );
    jobScheduleRequest.setRunSafeMode( baseJob.getEnableSafeMode().toString() );
    jobScheduleRequest.setGatheringMetrics( baseJob.getGatheringMetrics().toString() );
    jobScheduleRequest.setLogLevel( baseJob.getLogLevel() );

    JobTrigger jobTrigger = null;
    ComplexJobTriggerProxy complexJobTrigger = null;
    switch ( baseJob.getUiPassParam() ) {
      case RUN_ONCE:
        jobTrigger = new SimpleJobTrigger( baseJob.getStartDate(), null, 1, -1L );
        jobTrigger.setUiPassParam( baseJob.getUiPassParam().value() );
        setJobTriggerStartTime( baseJob, jobTrigger );
        jobScheduleRequest.setSimpleJobTrigger( (SimpleJobTrigger) jobTrigger );
        break;
      case SECONDS:
        jobTrigger = new SimpleJobTrigger( baseJob.getStartDate(), null,
          ( (SecondsJobScheduleRequest) baseJob ).getRepeatInterval(), -1L );
        jobTrigger.setUiPassParam( baseJob.getUiPassParam().value() );
        setJobTriggerStartTime( baseJob, jobTrigger );
        jobScheduleRequest.setSimpleJobTrigger( (SimpleJobTrigger) jobTrigger );
        break;
      case DAILY:
        DailyJobScheduleRequest dailyJobScheduleRequest = (DailyJobScheduleRequest) baseJob;
        if ( dailyJobScheduleRequest.getIsEveryNDays() && !dailyJobScheduleRequest.getIgnoreDST() ) {
          complexJobTrigger = new ComplexJobTriggerProxy();
          complexJobTrigger.setCronString( "TO_BE_GENERATED" );
          complexJobTrigger.setRepeatInterval( dailyJobScheduleRequest.getRepeatInterval() );
          complexJobTrigger.setUiPassParam( dailyJobScheduleRequest.getUiPassParam().value() );
          setJobTriggerStartTime( baseJob, complexJobTrigger );
          jobScheduleRequest.setComplexJobTrigger( complexJobTrigger );
        } else if ( dailyJobScheduleRequest.getIsEveryNDays() && !dailyJobScheduleRequest.getIgnoreDST() ) {
          jobTrigger = new SimpleJobTrigger( baseJob.getStartDate(), null,
            dailyJobScheduleRequest.getRepeatInterval(), -1L );
          jobTrigger.setUiPassParam( baseJob.getUiPassParam().value() );
          setJobTriggerStartTime( baseJob, jobTrigger );
          jobScheduleRequest.setSimpleJobTrigger( (SimpleJobTrigger) jobTrigger );
        } else {
          WeeklyJobScheduleRequest weeklyJobScheduleRequest =
            (WeeklyJobScheduleRequest) baseJob;
          complexJobTrigger = getComplexTriggerFromWeekly( weeklyJobScheduleRequest );
          complexJobTrigger.setUiPassParam( baseJob.getUiPassParam().value() );
          setJobTriggerStartTime( baseJob, complexJobTrigger );
          jobScheduleRequest.setComplexJobTrigger( complexJobTrigger );
        }
        break;
      case WEEKLY:
        WeeklyJobScheduleRequest weeklyJobScheduleRequest =
          (WeeklyJobScheduleRequest) baseJob;
        complexJobTrigger = getComplexTriggerFromWeekly( weeklyJobScheduleRequest );
        complexJobTrigger.setUiPassParam( baseJob.getUiPassParam().value() );
        setJobTriggerStartTime( baseJob, complexJobTrigger );
        break;
      case MONTHLY:
        MonthlyJobScheduleRequest monthlyJobScheduleRequest =
          (MonthlyJobScheduleRequest) baseJob;
        complexJobTrigger = getCompletxTriggerFromMonthly( monthlyJobScheduleRequest );
        complexJobTrigger.setUiPassParam( baseJob.getUiPassParam().value() );
        setJobTriggerStartTime( baseJob, complexJobTrigger );
        break;
      case YEARLY:
        YearlyJobScheduleRequest yearlyJobScheduleRequest =
          (YearlyJobScheduleRequest) baseJob;
        complexJobTrigger = getComplexTriggerFromYearly( yearlyJobScheduleRequest );
        complexJobTrigger.setUiPassParam( baseJob.getUiPassParam().value() );
        setJobTriggerStartTime( baseJob, complexJobTrigger );
        break;
      case CRON:
        CronJobScheduleRequest cronJobScheduleRequest =
          (CronJobScheduleRequest) baseJob;
        ComplexJobTrigger cronJobTrigger = new ComplexJobTrigger();
        cronJobTrigger.setCronString( cronJobScheduleRequest.getCronExpression() );
        cronJobTrigger.setUiPassParam( cronJobScheduleRequest.getUiPassParam().value() );
        setJobTriggerStartTime( baseJob, cronJobTrigger );
      default:
        throw new IllegalArgumentException( "Unsupported UI pass param: " + baseJob.getUiPassParam() );
    }

    return jobScheduleRequest;

  }

  public SimpleJobTrigger createSimpleJobTrigger( Object scheduleRequest ) {
    if ( scheduleRequest instanceof RunOnceJobScheduleRequest ) {
      RunOnceJobScheduleRequest req =
        (RunOnceJobScheduleRequest) scheduleRequest;
      return new SimpleJobTrigger(
        req.getStartDate(),
        null,
        0,
        0
      );
    } else if ( scheduleRequest instanceof SecondsJobScheduleRequest ) {
      SecondsJobScheduleRequest req =
        (SecondsJobScheduleRequest) scheduleRequest;
      return new SimpleJobTrigger(
        req.getStartDate(),
        null,
        req.getRepeatInterval(),
        0
      );

    } else {
      throw new IllegalArgumentException( "Unsupported schedule request type: " + scheduleRequest.getClass() );
    }
  }

  protected void setJobTriggerStartTime( BaseJob baseJob, JobTrigger trigger ) {
    // break the start date into its components and set them on the trigger
    if ( baseJob.getStartDate() != null ) {
      trigger.setStartTime( baseJob.getStartDate() );
      // do not correct these values; that will be done by the QuartzScheduler class
      trigger.setStartYear( baseJob.getStartDate().getYear() );
      trigger.setStartMonth( baseJob.getStartDate().getMonth() );
      trigger.setStartDay( baseJob.getStartDate().getDate() );
      trigger.setStartHour( baseJob.getStartDate().getHours() );
      trigger.setStartMin( baseJob.getStartDate().getMinutes() );
      trigger.setTimeZone( baseJob.getTimeZone() );
    }
  }

  protected void setJobTriggerStartTime( BaseJob baseJob, ComplexJobTriggerProxy trigger ) {
    // break the start date into its components and set them on the trigger
    if ( baseJob.getStartDate() != null ) {
      trigger.setStartTime( baseJob.getStartDate() );
      // do not correct these values; that will be done by the QuartzScheduler class
      trigger.setStartYear( baseJob.getStartDate().getYear() );
      trigger.setStartMonth( baseJob.getStartDate().getMonth() );
      trigger.setStartDay( baseJob.getStartDate().getDate() );
      trigger.setStartHour( baseJob.getStartDate().getHours() );
      trigger.setStartMin( baseJob.getStartDate().getMinutes() );
    }
  }

  protected ComplexJobTriggerProxy getComplexTriggerFromWeekly( WeeklyJobScheduleRequest weeklyJobScheduleRequest ) {
    ComplexJobTriggerProxy complexJobTrigger = new ComplexJobTriggerProxy();
    setJobTriggerStartTime( weeklyJobScheduleRequest, complexJobTrigger );

    int weekDayVariance = 0;

    if ( weeklyJobScheduleRequest.getTimeZone() != null ) {
      weekDayVariance = TimeUtil.getDayVariance( complexJobTrigger.getStartHour(), complexJobTrigger.getStartMin(),
        weeklyJobScheduleRequest.getTimeZone() );
    }
    // convert the days of week to the array of ints expected by the ComplexJobTriggerProxy
    int[] daysOfWeek = new int[ weeklyJobScheduleRequest.getDaysOfWeek().size() ];
    for ( int i = 0; i < weeklyJobScheduleRequest.getDaysOfWeek().size(); i++ ) {
      daysOfWeek[ i ] = weeklyJobScheduleRequest.getDaysOfWeek().get( i ).ordinal() + weekDayVariance;
    }
    complexJobTrigger.setDaysOfWeek( daysOfWeek );

    return complexJobTrigger;
  }

  protected ComplexJobTriggerProxy getCompletxTriggerFromMonthly( MonthlyJobScheduleRequest monthlyJobScheduleRequest ) {
    ComplexJobTriggerProxy complexJobTrigger = new ComplexJobTriggerProxy();
    setJobTriggerStartTime( monthlyJobScheduleRequest, complexJobTrigger );

    int weekDayVariance = 0;

    if ( monthlyJobScheduleRequest.getDayOfMonth() != null ) {
      int[] daysOfMonth = new int[ 1 ];
      daysOfMonth[ 0 ] = monthlyJobScheduleRequest.getDayOfMonth();
      complexJobTrigger.setDaysOfMonth( daysOfMonth );
    }

    if ( monthlyJobScheduleRequest.getWeekOfMonth() != null ) {
      // convert the week of month to the array of ints expected by the ComplexJobTriggerProxy
      int[] weeksOfMonth = new int[ 1 ];
      weeksOfMonth[ 0 ] = monthlyJobScheduleRequest.getWeekOfMonth().ordinal();
      complexJobTrigger.setWeeksOfMonth( weeksOfMonth );
    }

    if ( monthlyJobScheduleRequest.getTimeZone() != null ) {
      weekDayVariance = TimeUtil.getDayVariance( complexJobTrigger.getStartHour(), complexJobTrigger.getStartMin(),
        monthlyJobScheduleRequest.getTimeZone() );
    }

    int[] daysOfWeek = new int[ 1 ];
    daysOfWeek[ 0 ] = monthlyJobScheduleRequest.getDayOfWeek().ordinal() + weekDayVariance;
    complexJobTrigger.setDaysOfWeek( daysOfWeek );
    return complexJobTrigger;
  }

  protected ComplexJobTriggerProxy getComplexTriggerFromYearly( YearlyJobScheduleRequest yearlyJobScheduleRequest ) {
    ComplexJobTriggerProxy complexJobTrigger = new ComplexJobTriggerProxy();
    setJobTriggerStartTime( yearlyJobScheduleRequest, complexJobTrigger );

    int weekDayVariance = 0;

    if ( yearlyJobScheduleRequest.getDayOfMonth() != null ) {
      int[] daysOfMonth = new int[ 1 ];
      daysOfMonth[ 0 ] = yearlyJobScheduleRequest.getDayOfMonth();
      complexJobTrigger.setDaysOfMonth( daysOfMonth );
    }

    if ( yearlyJobScheduleRequest.getWeekOfMonth() != null ) {
      // convert the week of month to the array of ints expected by the ComplexJobTriggerProxy
      int[] weeksOfMonth = new int[ 1 ];
      weeksOfMonth[ 0 ] = yearlyJobScheduleRequest.getWeekOfMonth().ordinal();
      complexJobTrigger.setWeeksOfMonth( weeksOfMonth );
    }

    if ( yearlyJobScheduleRequest.getTimeZone() != null ) {
      weekDayVariance = TimeUtil.getDayVariance( complexJobTrigger.getStartHour(), complexJobTrigger.getStartMin(),
        yearlyJobScheduleRequest.getTimeZone() );
    }

    int[] daysOfWeek = new int[ 1 ];
    daysOfWeek[ 0 ] = yearlyJobScheduleRequest.getDayOfWeek().ordinal() + weekDayVariance;
    complexJobTrigger.setDaysOfWeek( daysOfWeek );
    return complexJobTrigger;
  }

}
