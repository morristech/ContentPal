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

package org.dmfs.android.contactspal.data.membership;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.TransactionContext;


/**
 * @author Marten Gajda
 */
public final class GroupRowMembership implements RowData<ContactsContract.Data>
{
    private final RowSnapshot<ContactsContract.Groups> mGroup;


    public GroupRowMembership(@NonNull RowSnapshot<ContactsContract.Groups> group)
    {
        mGroup = group;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        // this data row refers to the given group
        return transactionContext.resolved(mGroup.reference())
                .builderWithReferenceData(transactionContext, builder, ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID);
    }
}
