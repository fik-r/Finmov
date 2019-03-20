package com.fizus.mobiledev.finmov;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRx() {
        String[] strings = { "A", "B"};
        Observable.just(strings)
                .flatMap(new Function<String[], ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String[] strings) throws Exception {
                        return Observable.fromArray(strings);
                    }
                }).subscribe(new Consumer<String>() {
                                 @Override
                                 public void accept(String s) throws Exception {
                                     System.out.println(s);
                                 }
                             }, new Consumer<Throwable>() {
                                 @Override
                                 public void accept(Throwable throwable) throws Exception {
                                     System.out.println(throwable.getLocalizedMessage());
                                 }
                             }
        );
    }
}