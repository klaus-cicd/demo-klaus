package com.klaus.demo.helloworld.test;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

public class SystemInfoExample {
    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();

        // 获取 CPU 信息
        CentralProcessor processor = hardware.getProcessor();
        System.out.println("CPU Cores: " + processor.getLogicalProcessorCount());

        // 获取内存信息
        GlobalMemory memory = hardware.getMemory();
        System.out.println("Total Memory: " + memory.getTotal() / 1024 / 1024 / 1024 + "GB");
        System.out.println("Available Memory: " + memory.getAvailable() / 1024 / 1024 / 1024 + "GB");

        // 获取硬盘信息
        hardware.getDiskStores().forEach(disk -> {
            System.out.println("Disk: " + disk.getName());
            System.out.println("Total Space: " + disk.getSize() / 1024 / 1024 / 1024 + "GB");
        });
    }
}
