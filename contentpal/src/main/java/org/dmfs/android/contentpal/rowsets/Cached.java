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

package org.dmfs.android.contentpal.rowsets;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.ClosableIterator;
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.tools.FakeClosable;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


/**
 * A {@link RowSet} Decorator which caches the content of the decorated {@link RowSet}.
 *
 * @author Marten Gajda
 */
public final class Cached<T> implements RowSet<T>
{
    private final RowSet<T> mDelegate;
    private List<RowSnapshot<T>> mSnapshots;


    public Cached(RowSet<T> delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public ClosableIterator<RowSnapshot<T>> iterator()
    {
        if (mSnapshots == null)
        {
            // pull all the snapshots
            ClosableIterator<RowSnapshot<T>> iterator = mDelegate.iterator();
            try
            {
                try
                {
                    List<RowSnapshot<T>> snapshots = new LinkedList<>();

                    while (iterator.hasNext())
                    {
                        snapshots.add(iterator.next());
                    }

                    mSnapshots = snapshots;
                }
                finally
                {
                    iterator.close();
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException("IOException without doing any IO");
            }
        }
        return new FakeClosable<>(mSnapshots.iterator());
    }
}
