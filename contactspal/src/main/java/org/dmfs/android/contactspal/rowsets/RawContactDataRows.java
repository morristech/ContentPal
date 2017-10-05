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

package org.dmfs.android.contactspal.rowsets;

import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.tables.Data;
import org.dmfs.android.contentpal.ClosableIterator;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.predicates.ReferringTo;
import org.dmfs.android.contentpal.references.RowSnapshotReference;
import org.dmfs.android.contentpal.rowsets.Cached;
import org.dmfs.android.contentpal.rowsets.QueryRowSet;


/**
 * The {@link RowSet} of the {@link ContactsContract.Data} rows of a specific raw contact.
 * <p>
 * Note, the result is not cached. Iterating this multiple times, will query the content provider each time. Use {@link Cached} to cache the result.
 *
 * @author Marten Gajda
 */
public final class RawContactDataRows implements RowSet<ContactsContract.Data>
{
    private final View<ContactsContract.Data> mDataView;
    private final Predicate mPredicate;


    /**
     * Creates a {@link RowSet} of all {@link ContactsContract.Data} rows of the given raw contact.
     *
     * @param dataView
     *         A {@link View} onto the {@link ContactsContract.Data} table, like {@link Data}.
     * @param rawContact
     *         The {@link RowSnapshot} of a RawContact.
     */
    public RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull RowSnapshot<ContactsContract.RawContacts> rawContact)
    {
        this(dataView, rawContact, new AnyOf());
    }


    /**
     * Creates a {@link RowSet} of all {@link ContactsContract.Data} rows of the given raw contact.
     *
     * @param dataView
     *         A {@link View} onto the {@link ContactsContract.Data} table, like {@link Data}.
     * @param rawContact
     *         The {@link RowReference} of a RawContact.
     */
    public RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull RowReference<ContactsContract.RawContacts> rawContact)
    {
        this(dataView, rawContact, new AnyOf());
    }


    /**
     * Creates a {@link RowSet} of all {@link ContactsContract.Data} rows of the given raw contact.
     *
     * @param dataView
     *         A {@link View} onto the {@link ContactsContract.Data} table, like {@link Data}.
     * @param rawContact
     *         The {@link RowSnapshot} of a RawContact.
     * @param predicate
     *         A {@link Predicate} to filter the data rows to return.
     */
    public RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull RowSnapshot<ContactsContract.RawContacts> rawContact, @NonNull Predicate predicate)
    {
        this(dataView, new RowSnapshotReference<>(rawContact), predicate);
    }


    /**
     * Creates a {@link RowSet} of all {@link ContactsContract.Data} rows of the given raw contact.
     *
     * @param dataView
     *         A {@link View} onto the {@link ContactsContract.Data} table, like {@link Data}.
     * @param rawContactReference
     *         The {@link RowReference} of a RawContact.
     * @param predicate
     *         A {@link Predicate} to filter the data rows to return.
     */
    public RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull RowReference<ContactsContract.RawContacts> rawContactReference, @NonNull Predicate predicate)
    {
        this(dataView, new AllOf(new ReferringTo<>(BaseColumns._ID, rawContactReference), predicate));
    }


    private RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull Predicate predicate)
    {
        mDataView = dataView;
        mPredicate = predicate;
    }


    @NonNull
    @Override
    public ClosableIterator<RowSnapshot<ContactsContract.Data>> iterator()
    {
        return new QueryRowSet<>(mDataView, mPredicate).iterator();
    }
}
