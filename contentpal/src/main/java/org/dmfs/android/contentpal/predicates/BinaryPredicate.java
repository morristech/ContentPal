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

package org.dmfs.android.contentpal.predicates;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.iterables.elementary.Seq;
import org.dmfs.jems.iterable.composite.Joined;
import org.dmfs.jems.iterable.decorators.Mapped;


/**
 * A {@link Predicate} which connects a number of given predicates with the given binary operator. Like this:
 * <pre>{@code
 * (predicate_1) operator (predicate_2) operator (predicate_3) … operator (predicate_n)
 * }</pre>
 * <p>
 * If no predicates are given this always evaluates to "1".
 *
 * @author Marten Gajda
 */
public final class BinaryPredicate implements Predicate
{
    private final Predicate[] mPredicates;
    private final String mOperator;


    public BinaryPredicate(@NonNull String operator, @NonNull Predicate... predicates)
    {
        mOperator = operator;
        mPredicates = predicates;
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        if (mPredicates.length == 0)
        {
            // if no predicates are present, all predicates are satisfied
            return "1";
        }
        if (mPredicates.length == 1)
        {
            return mPredicates[0].selection(transactionContext);
        }
        StringBuilder result = new StringBuilder(mPredicates.length * 24);
        result.append("( ");
        result.append(mPredicates[0].selection(transactionContext));
        for (int i = 1, count = mPredicates.length; i < count; ++i)
        {
            result.append(" ) ");
            result.append(mOperator);
            result.append(" ( ");
            result.append(mPredicates[i].selection(transactionContext));
        }
        result.append(" )");
        return result;

    }


    @NonNull
    @Override
    public Iterable<Argument> arguments(@NonNull final TransactionContext transactionContext)
    {
        return new Joined<>(
                new Mapped<>(
                        predicate -> predicate.arguments(transactionContext),
                        new Seq<>(mPredicates)));
    }
}
