//package com.demo.security.util;
//
//public class SerialNumberGenerator{
//    private final long datacenterId; // 数据中心ID
//    private final long machineId; // 机器ID
//    private long sequence = 0L; // 序列号
//    private long lastTimestamp = -1L; // 上次生成ID的时间戳
//
//    private static final long TWEPOCH = 1288834974657L; // 起始时间戳，用于减少时间戳位数
//
//    private static final long DATACENTER_ID_BITS = 5L; // 数据中心ID位数
//    private static final long MACHINE_ID_BITS = 5L; // 机器ID位数
//    private static final long SEQUENCE_BITS = 12L; // 序列号位数
//
//    private static final long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS); // 最大数据中心ID
//    private static final long MAX_MACHINE_ID = -1L ^ (-1L << MACHINE_ID_BITS); // 最大机器ID
//    private static final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BITS); // 最大序列号
//
//    private static final long DATACENTER_ID_SHIFT = MACHINE_ID_BITS + SEQUENCE_BITS; // 数据中心ID左移位数
//    private static final long MACHINE_ID_SHIFT = SEQUENCE_BITS; // 机器IDShift位数
//    private static final long TIMESTAMP_LEFT_SHIFT = DATACENTER_ID_BITS + MACHINE_ID_BITS + SEQUENCE_BITS; // 时间戳左移位数
//
//    public SerialNumberGenerator(long datacenterId, long machineId) {
//        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
//            throw new IllegalArgumentException("Datacenter ID can't be greater than " + MAX_DATACENTER_ID + " or less than 0");
//        }
//        if (machineId > MAX_MACHINE_ID || machineId < 0) {
//            throw new IllegalArgumentException("Machine ID can't be greater than " + MAX_MACHINE_ID + " or less than 0");
//        }
//        this.datacenterId = datacenterId;
//        this.machineId = machineId;
//    }
//
//    public synchronized long nextId() {
//        long timestamp = timeGen(); // 获取当前时间戳
//        if (timestamp < lastTimestamp) {
//            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
//        }
//
//        if (lastTimestamp == timestamp) {
//            // 相同毫秒内，序列号自增
//            sequence = (sequence + 1) & MAX_SEQUENCE;
//            if (sequence == 0) {
//                // 同一毫秒的序列号已达到最大值，等待下一毫秒
//                timestamp = tilNextMillis(lastTimestamp);
//            }
//            else {
//                // 不同毫秒内，序列号重置为0
//                sequence = 0L;
//            }
//
//            lastTimestamp = timestamp; // 更新上次生成ID的时间戳
//
//            // 组装ID
//            return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) |
//                    (datacenterId << DATACENTER_ID_SHIFT) |
//                    (machineId << MACHINE_ID_SHIFT) |
//                    sequence;
//        }
//
//        private long tilNextMillis(long lastTimestamp) {
//            long timestamp = timeGen();
//            while (timestamp <= lastTimestamp) {
//                timestamp = timeGen();
//            }
//            return timestamp;
//        }
//
//        private long timeGen() {
//            return System.currentTimeMillis();
//        }
//    }