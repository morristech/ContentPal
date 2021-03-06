/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.android.contactspal.data.postal;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.Composite;


/**
 * Simple {@link StructuredPostalData} which contains only the most common values and sets everything else to {@code null}.
 * <p>
 * Use {@link Typed} or {@link Custom} to add a type.
 *
 * @author Marten Gajda
 */
public final class SimplePostalData implements StructuredPostalData
{
    private final RowData<ContactsContract.Data> mDelegate;


    public SimplePostalData(@Nullable CharSequence street, @Nullable CharSequence postcode, @Nullable CharSequence city, @Nullable CharSequence country)
    {
        mDelegate = new Composite<>(
                new StreetData(street),
                new NeighborhoodData(null),
                new RegionData(null),
                new PostcodeData(postcode),
                new PoBoxData(null),
                new CityData(city),
                new CountryData(country)
        );
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder);
    }
}
