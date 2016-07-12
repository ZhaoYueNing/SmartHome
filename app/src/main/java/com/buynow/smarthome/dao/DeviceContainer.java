package com.buynow.smarthome.dao;

import com.buynow.smarthome.domain.Device;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by buynow on 16-6-9.
 * 模块容器
 * 封装sql处理
 */
public class DeviceContainer {
    private static List<Device> sDeviceList = new LinkedList<Device>();

    static {
        //从数据库数据初始化容器
        initData();
    }


    private static void initData() {
        //TODO 虚拟数据
        Device device = new Device();
        device.setId(UUID.randomUUID().toString());
        device.setName("客厅");

        addDevice(device);
    }

    /**
     * 添加设备
     */
    public static void addDevice( Device device) {
        DeviceContainer.sDeviceList.add(device);
        //sql处理

    }

    /**
     * 移除操作
     * @param device
     * @return 删除成功返回true
     */
    public static boolean  removeDervice(Device device) {
        return sDeviceList.remove(device);
    }

    public static List<Device> get() {
        return sDeviceList;
    }


}
