package net.aung.moviemaniac.data.restapi;

import net.aung.moviemaniac.events.DataEvent;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by aung on 12/16/15.
 */
public abstract class MovieApiCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        T responseBody = response.body();
        if (responseBody == null) {
            DataEvent.FailedToLoadDataEvent event = new DataEvent.FailedToLoadDataEvent(response.message());
            EventBus.getDefault().post(event);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        DataEvent.FailedToLoadDataEvent event = new DataEvent.FailedToLoadDataEvent(throwable.getMessage());
        EventBus.getDefault().post(event);
    }
}
