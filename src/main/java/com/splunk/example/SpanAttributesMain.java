package com.splunk.example;

import java.util.concurrent.TimeUnit;

public class SpanAttributesMain {

    public static void main(String[] args) throws Exception {
        new SpanAttributesMain().run();

    }

    private void run() throws Exception {
        while(true){
            TimeUnit.SECONDS.sleep(2);

        }
    }

}
