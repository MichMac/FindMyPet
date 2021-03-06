package com.example.findmypet.utils;

import androidx.annotation.Nullable;

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
public class EventWrapper<T> {

    private T mContent;

    private boolean hasBeenHandled = false;


    public EventWrapper(T content) {
        if (content == null) {
            throw new IllegalArgumentException("null values in Event are not allowed.");
        }
        mContent = content;
    }

    @Nullable
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return mContent;
        }
    }

    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }
}
