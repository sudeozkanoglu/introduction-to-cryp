package org.app.g201210034.results.easy_type.result;

public class DataResult<T> extends Result{
    private final T data;
    public DataResult(T data,Result result) {
        super(result);
        this.data = data;
    }

    public DataResult(T data, int resultCode, String resultText) {
        super(resultCode, resultText);
        this.data = data;
    }

    public T getData(){return data;}
}
