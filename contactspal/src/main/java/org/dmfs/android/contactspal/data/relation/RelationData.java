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

package org.dmfs.android.contactspal.data.relation;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;


/**
 * Data of a {@link ContactsContract.CommonDataKinds.Relation} row.
 * <p>
 * Use {@link Custom} to add a custom type.
 *
 * @author Marten Gajda
 */
public final class RelationData implements RowData<ContactsContract.Data>
{
    private final int mType;
    private final CharSequence mName;


    public RelationData(int type, @NonNull CharSequence name)
    {
        mType = type;
        mName = name;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder
                .withValue(ContactsContract.CommonDataKinds.Relation.MIMETYPE, ContactsContract.CommonDataKinds.Relation.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Relation.TYPE, mType)
                .withValue(ContactsContract.CommonDataKinds.Relation.NAME, mName.toString());
    }
}
