package com.cqnu.sunxt.telephonebook.base;

import android.support.annotation.NonNull;

public class BaseDataSource {

    public interface GetDataSourceCallback<T> {

        void onLoaded(@NonNull T data);

        void onDataNotAvailable();
    }

    interface SetDataSourceCallback<T> {

        void onUpdated(@NonNull T newData);

        void onError();

    }
}
