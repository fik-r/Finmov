package com.fizus.mobiledev.finmov.utils;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler ui();

    Scheduler io();

    Scheduler newThread();
}
