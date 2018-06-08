package xyz.vivekc.popularmovies.repository.api;

public class ApiResponse<T> {

    public enum State {
        LOADING, SUCCESS, FAILURE, ERROR //Error when API hit is success but the API throws some error
    }

    public T data = null;
    public Throwable throwable = null;
    public State currentState = State.LOADING;
}