package com.li.redis.controller;

import org.redisson.api.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class RedissonController {

    @Resource
    private RedissonClient redisson;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 可重入锁
    @RequestMapping("/hello")
    public String hello() {

        // 获取一把锁，只要锁的名字一样，就是同一把锁
        RLock lock = redisson.getLock("hello-lock");

        // 加锁，阻塞式等待
        lock.lock();
        // lock.lock(10, TimeUnit.SECONDS);
        try {
            System.out.println("加锁成功，执行业务逻辑中……" + Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放锁……" + Thread.currentThread().getId());
            lock.unlock();
        }
        return "Hello World";
    }

    // 读写锁
    @RequestMapping("/write")
    public String write() {
        String uuid = UUID.randomUUID().toString();
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        // 改数据加写锁
        RLock rLock = readWriteLock.writeLock();
        rLock.lock();
        try {
            Thread.sleep(30000);
            stringRedisTemplate.opsForValue().set("uuid", uuid);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放写锁
            rLock.unlock();
        }

        return uuid;
    }

    @RequestMapping("/read")
    public String read() {
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        // 读数据加读锁
        RLock rLock = readWriteLock.readLock();
        rLock.lock();
        try {
            return stringRedisTemplate.opsForValue().get("uuid");
        } finally {
            // 释放读锁
            rLock.unlock();
        }
    }

    // 闭锁
    @RequestMapping("/lockDoor")
    public String lockDoor() throws Exception {
        // 声明闭锁
        RCountDownLatch countDownLatch = redisson.getCountDownLatch("door-lock");
        // 等待几个锁的释放
        countDownLatch.trySetCount(5);
        // 等待闭锁都完成
        countDownLatch.await();

        return "放假了……";
    }

    @RequestMapping("/go/{id}")
    public String go(@PathVariable("id") Long id) {
        // 声明闭锁
        RCountDownLatch countDownLatch = redisson.getCountDownLatch("door-lock");
        // 技术减1
        countDownLatch.countDown();
        return id + "走了";
    }

    // 信号量
    @RequestMapping("/park")
    public String park() throws Exception {
        RSemaphore semaphore = redisson.getSemaphore("park");
        // 获取一个信号，阻塞式等待
        // semaphore.acquire();
        // 非阻塞式等待
        boolean b = semaphore.tryAcquire();
        if (b) {
            // 执行业务
            return "park成功";
        }
        return "park失败";
    }

    @RequestMapping("/leave")
    public String leave() {
        RSemaphore semaphore = redisson.getSemaphore("park");
        // 释放一个信号
        semaphore.release();
        return "leave";
    }
}
