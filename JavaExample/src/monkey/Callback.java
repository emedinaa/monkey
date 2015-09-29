package monkey;

/**
 * Created by emedinaa on 12/09/15.
 */
public interface Callback<T> {
    /** Successful HTTP response. */
    void onResponse(Response<T> response);

    /** Invoked when a network or unexpected exception occurred during the HTTP request. */
    void onFailure(Throwable t);
}
