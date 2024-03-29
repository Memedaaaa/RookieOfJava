package com.lg.jdk.polling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class NormalRoundRobin {


    private List<Server> servers;

    private int currentIndex;
    private int totalServer;

    public NormalRoundRobin() {
        servers = new ArrayList<>();
        servers.add(Server.builder().ip("192.168.1.2").build());
        servers.add(Server.builder().ip("192.168.1.3").build());
        servers.add(Server.builder().ip("192.168.1.4").build());
        servers.add(Server.builder().ip("192.168.1.5").build());
        servers.add(Server.builder().ip("192.168.1.6").build());
        servers.add(Server.builder().ip("192.168.1.7").build());
        servers.add(Server.builder().ip("192.168.1.8").build());
        totalServer = servers.size();
        currentIndex = totalServer - 1;
    }


    // 轮询
    public Server round() {
        currentIndex = (currentIndex + 1) % totalServer;
        return servers.get(currentIndex);
    }


    public static void main(String[] args) {
        final NormalRoundRobin r = new NormalRoundRobin();
        // 不带并发的轮询
        for (int i = 0; i < 14; i++) {
            System.out.println(r.round());
        }

        System.out.println();
        System.out.println("==========================");
        System.out.println();

        final CyclicBarrier b = new CyclicBarrier(14);

        // 带并发的轮询
        for (int i = 0; i < 14; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        b.await();
                        System.out.println(Thread.currentThread().getName() + " " + r.round());
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }, "thread" + i).start();
        }
    }
}
