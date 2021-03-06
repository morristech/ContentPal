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

package org.dmfs.android.contentpal.tables;

import android.net.Uri;
import android.provider.ContactsContract;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SyncedTest
{
    @Test
    public void testInsertOperation() throws Exception
    {
        InsertOperation<Object> dummyResultOperation = dummy(InsertOperation.class);
        Table<Object> mockTable = failingMock(Table.class);
        doReturn(dummyResultOperation).when(mockTable).insertOperation(argThat(new UriParamsArgumentMatcher()));

        assertThat(new Synced<>(mockTable).insertOperation(new EmptyUriParams()), sameInstance(dummyResultOperation));
    }


    @Test
    public void testUpdateOperation() throws Exception
    {
        Operation<Object> dummyResultOperation = dummy(Operation.class);
        Predicate dummyPredicate = dummy(Predicate.class);
        Table<Object> mockTable = failingMock(Table.class);
        doReturn(dummyResultOperation).when(mockTable).updateOperation(argThat(new UriParamsArgumentMatcher()), same(dummyPredicate));

        assertThat(new Synced<>(mockTable).updateOperation(new EmptyUriParams(), dummyPredicate), sameInstance(dummyResultOperation));
    }


    @Test
    public void testDeleteOperation() throws Exception
    {
        Operation<Object> dummyResultOperation = dummy(Operation.class);
        Predicate dummyPredicate = dummy(Predicate.class);
        Table<Object> mockTable = failingMock(Table.class);
        doReturn(dummyResultOperation).when(mockTable).deleteOperation(argThat(new UriParamsArgumentMatcher()), same(dummyPredicate));

        assertThat(new Synced<>(mockTable).deleteOperation(new EmptyUriParams(), dummyPredicate), sameInstance(dummyResultOperation));
    }


    @Test
    public void testAssertOperation() throws Exception
    {
        Operation<Object> dummyResultOperation = dummy(Operation.class);
        Predicate dummyPredicate = dummy(Predicate.class);
        Table<Object> mockTable = failingMock(Table.class);
        doReturn(dummyResultOperation).when(mockTable).assertOperation(argThat(new UriParamsArgumentMatcher()), same(dummyPredicate));

        assertThat(new Synced<>(mockTable).assertOperation(new EmptyUriParams(), dummyPredicate), sameInstance(dummyResultOperation));
    }


    private static class UriParamsArgumentMatcher implements ArgumentMatcher<UriParams>
    {
        @Override
        public boolean matches(UriParams argument)
        {
            Uri uri = argument.withParam(new Uri.Builder()).build();
            return uri.getBooleanQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, false);
        }
    }

}