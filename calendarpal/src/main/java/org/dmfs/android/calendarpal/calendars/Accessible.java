/*
 * Copyright 2018 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.android.calendarpal.calendars;

import android.content.ContentProviderOperation;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.TransactionContext;


/**
 * {@link CalendarRowData} decorator to set the access level of a calendar.
 *
 * @author Marten Gajda
 */
public final class Accessible implements CalendarRowData
{
    private final CalendarRowData mDelegate;
    private final int mAccessLevel;


    public Accessible(int accessLevel, @NonNull CalendarRowData delegate)
    {
        mDelegate = delegate;
        mAccessLevel = accessLevel;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder).withValue(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, mAccessLevel);
    }
}
