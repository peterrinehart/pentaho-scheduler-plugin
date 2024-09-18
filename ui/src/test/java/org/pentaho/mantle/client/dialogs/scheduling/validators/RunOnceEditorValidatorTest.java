/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2024 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.mantle.client.dialogs.scheduling.validators;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.mantle.client.dialogs.scheduling.RunOnceEditor;
import org.pentaho.mantle.client.dialogs.scheduling.ScheduleEditor;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith( GwtMockitoTestRunner.class )
public class RunOnceEditorValidatorTest {

  @Test
  public void testIsValid() throws Exception {
    final RunOnceEditor runOnceEditor = mock( RunOnceEditor.class );
    final ScheduleEditor scheduleEditor = mock( ScheduleEditor.class );
    final ListBox timeZonePicker = mock( ListBox.class );

    when( scheduleEditor.getRunOnceEditor() ).thenReturn( runOnceEditor );
    when( scheduleEditor.getTimeZonePicker() ).thenReturn( timeZonePicker );
    when( timeZonePicker.getSelectedValue() ).thenReturn( "UTC" );
    final RunOnceEditorValidator validator = spy( new RunOnceEditorValidator( scheduleEditor, runOnceEditor ) );

    when( runOnceEditor.getStartDate() ).thenReturn( null );
    assertFalse( validator.isValid() );

    Calendar calendar = Calendar.getInstance();
    calendar.add( Calendar.SECOND, -1 );
    when( validator.getNowInTz( anyString() ) ).thenReturn( new Date() );
    when( runOnceEditor.getStartDate() ).thenReturn( calendar.getTime() );
    String startTime = DateTimeFormat.getFormat( "hh:mm:ss a" ).
      format( calendar.getTime() ).toLowerCase();
    when( runOnceEditor.getStartTime() ).thenReturn( startTime );
    assertFalse( validator.isValid() );

    calendar.add( Calendar.MINUTE, 1 );
    when( runOnceEditor.getStartDate() ).thenReturn( calendar.getTime() );
    when( runOnceEditor.getStartTime() ).thenReturn( DateTimeFormat.getFormat( "hh:mm:ss a" ).
      format( calendar.getTime() ).toLowerCase() );
    assertTrue( validator.isValid() );
  }
}