package com.uvic.concurrency;

import java.util.concurrent.Future;

public interface Proxy<T> {

    Future<T> processMessage(final String message);

}
