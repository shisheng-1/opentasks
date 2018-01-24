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

package org.dmfs.tasks.model.adapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.OnContentChangeListener;
import org.dmfs.tasks.utils.BooleanBinaryLong;


/**
 * Knows how to load and store a {@link Boolean} value in a certain field of a {@link ContentSet}.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class BooleanFieldAdapter extends FieldAdapter<Boolean>
{

    /**
     * The field name this adapter uses to store the values.
     */
    private final String mFieldName;

    /**
     * The default value, if any.
     */
    private final Boolean mDefaultValue;


    /**
     * Constructor for a new IntegerFieldAdapter without default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     */
    public BooleanFieldAdapter(String fieldName)
    {
        if (fieldName == null)
        {
            throw new IllegalArgumentException("fieldName must not be null");
        }
        mFieldName = fieldName;
        mDefaultValue = null;
    }


    /**
     * Constructor for a new BooleanFieldAdapter with default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     * @param defaultValue
     *         The default value.
     */
    public BooleanFieldAdapter(String fieldName, Boolean defaultValue)
    {
        if (fieldName == null)
        {
            throw new IllegalArgumentException("fieldName must not be null");
        }
        mFieldName = fieldName;
        mDefaultValue = defaultValue;
    }


    @Override
    public Boolean get(@NonNull ContentSet values)
    {
        Integer value = values.getAsInteger(mFieldName);

        return value != null && value > 0;
    }


    @Override
    public Boolean get(@NonNull Cursor cursor)
    {
        int columnIdx = cursor.getColumnIndex(mFieldName);
        if (columnIdx < 0)
        {
            throw new IllegalArgumentException("The fieldName column missing in cursor.");
        }
        return !cursor.isNull(columnIdx) && cursor.getInt(columnIdx) > 0;
    }


    @Override
    public Boolean getDefault(@NonNull ContentSet values)
    {
        return mDefaultValue;
    }


    @Override
    public void set(@NonNull ContentSet values, @Nullable Boolean value)
    {
        values.put(mFieldName, new BooleanBinaryLong(value).value());
    }


    @Override
    public void set(@NonNull ContentValues values, @Nullable Boolean value)
    {
        values.put(mFieldName, new BooleanBinaryLong(value).value());
    }


    @Override
    public void registerListener(@NonNull ContentSet values, @NonNull OnContentChangeListener listener, boolean initalNotification)
    {
        values.addOnChangeListener(listener, mFieldName, initalNotification);
    }


    @Override
    public void unregisterListener(@NonNull ContentSet values, @NonNull OnContentChangeListener listener)
    {
        values.removeOnChangeListener(listener, mFieldName);
    }


    public String getFieldName()
    {
        return mFieldName;
    }
}
