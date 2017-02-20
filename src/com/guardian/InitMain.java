package com.guardian;

public class InitMain{
    public static Thread core;
    public static void main(String[]args){
        new Thread(){
            @Override
            public void run(){
                new Main();
            }
        }.start();


    }
}
