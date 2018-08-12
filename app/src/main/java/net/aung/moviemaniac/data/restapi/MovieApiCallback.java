package net.aung.moviemaniac.data.restapi;

import net.aung.moviemaniac.events.DataEvent;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aung on 12/16/15.
 */
public abstract class MovieApiCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T responseBody = response.body();
        if (responseBody == null) {
            DataEvent.FailedToLoadDataEvent event = new DataEvent.FailedToLoadDataEvent(response.message());
            EventBus.getDefault().post(event);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        DataEvent.FailedToLoadDataEvent event = new DataEvent.FailedToLoadDataEvent(t.getMessage());
        EventBus.getDefault().post(event);
    }
}
