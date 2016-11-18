package com.s.z.j.entity;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.Arrays;

/**
 * 设备信息
 * Created by szj on 2015/7/21.
 */
@Table(name = "Info")
public class Info extends BaseEntity{

    @Column(name = "deviceId")
    private String deviceId;//  设备唯一id
    @Column(name = "facsimileId")
    private String facsimileId;//android_id
    @Column(name = "board")
    private String board;//主板
    @Column(name = "brand")
    private String brand;//BRAND 运营商
    @Column(name = "cup_abi")
    private String cup_abi;//BRAND 运营商
    @Column(name = "cup_abi2")
    private String cup_abi2;//BRAND 运营商
    @Column(name = "device")
    private String device;//DEVICE 驱动
    @Column(name = "display")
    private String display;//DISPLAY 显示
    @Column(name = "fingerprint")
    private String fingerprint;//指纹
    @Column(name = "host")
    private String host;//HARDWARE 硬件
    @Column(name = "hardwareId")
    private String hardwareId;//HARDWARE 硬件
    @Column(name = "hardware")
    private String hardware;////HARDWARE 硬件
    @Column(name = "manufacturer")
    private String manufacturer;//MANUFACTURER 生产厂家
    @Column(name = "model")
    private String model;//机型
    @Column(name = "product")
    private String product;//机型
    @Column(name = "tags")
    private String tags;//机型
    @Column(name = "time")
    private long time;//机型
    @Column(name = "type")
    private String type;//机型
    @Column(name = "user")
    private String user;//机型
    @Column(name = "bootloader")
    private String bootloader;//主板
    @Column(name = "radio")
    private String radio;//机型
    @Column(name = "version_release")
    private String version_release;//固件版本
    @Column(name = "version_codename")
    private String version_codename;//固件版本
    @Column(name = "version_incremental")
    private String version_incremental;//基带版本
    @Column(name = "version_sdk")
    private String version_sdk;//SDK版本
    @Column(name = "version_sdk_int")
    private int version_sdk_int;//SDK版本

    @Column(name = "MaxCpuFreq")
    private int maxCpuFreq;//CPU最大频率
    @Column(name = "MinCpuFreq")
    private int minCpuFreq;//CPU最小频率
    @Column(name = "CurCpuFreq")
    private int curCpuFreq;//CPU当前频率
    @Column(name = "CpuName")
    private String cpuName;//CPU名字
    @Column(name = "cpuUsage")
    private String cpuUsage;//CPU使用率
    /**内存条总大小*/
    @Column(name = "cpuUsage")
    private String ram_total;
    /**内存条剩余大小*/
    @Column(name = "cpuUsage")
    private String ram_free;
    /**内存卡总大小*/
    @Column(name = "cpuUsage")
    private String disk_total;
    /**内存卡剩余大小*/
    @Column(name = "cpuUsage")
    private String disk_free;




    @Column(name = "Version")
    private String version[];//系统的版本信息
    @Column(name = "OtherInfo")
    private String otherInfo[];//mac地址和开机时间
    @Column(name = "currentVolume")
    private int currentVolume;//当前音量
    @Column(name = "city")
    private String city;//城市
    @Column(name = "latitude")
    private String latitude;//纬度
    @Column(name = "longitude")
    private String longitude;//经度
    @Column(name = "width") //宽度
    private int width;
    @Column(name = "height") //高度
    private int height;
    @Column(name = "density")  //密度
    private float density;
    @Column(name = "brightness")  //亮度
    private int brightness;
    @Column(name = "orientation")//
    private int orientation;
    @Column(name = "facing")//
    private int facing;
    @Column(name = "canDisableShutterSound")//
    private boolean canDisableShutterSound;
    @Column(name = "cpu_temp")//CPU温度
    private String cpu_temp;

    @Column(name = "netavailable")
    private boolean  netavailable;//有没有网
    @Column(name = "gpsIsOpen")
    private boolean gpsIsOpen;//GPS是不是打开
    @Column(name = "wifiavailable")
    private boolean wifiavailable;//WIFI是不是打开
    @Column(name = "sdCardIsExist")
    private boolean sdCardIsExist;//有没有SD卡



    @Column(name = "CameraIsUse")
    private boolean cameraIsUse;//摄相头可以使用


    public Info() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFacsimileId() {
        return facsimileId;
    }

    public void setFacsimileId(String facsimileId) {
        this.facsimileId = facsimileId;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCup_abi() {
        return cup_abi;
    }

    public void setCup_abi(String cup_abi) {
        this.cup_abi = cup_abi;
    }

    public String getCup_abi2() {
        return cup_abi2;
    }

    public void setCup_abi2(String cup_abi2) {
        this.cup_abi2 = cup_abi2;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBootloader() {
        return bootloader;
    }

    public void setBootloader(String bootloader) {
        this.bootloader = bootloader;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getVersion_release() {
        return version_release;
    }

    public void setVersion_release(String version_release) {
        this.version_release = version_release;
    }

    public String getVersion_codename() {
        return version_codename;
    }

    public void setVersion_codename(String version_codename) {
        this.version_codename = version_codename;
    }

    public String getVersion_incremental() {
        return version_incremental;
    }

    public void setVersion_incremental(String version_incremental) {
        this.version_incremental = version_incremental;
    }

    public String getVersion_sdk() {
        return version_sdk;
    }

    public void setVersion_sdk(String version_sdk) {
        this.version_sdk = version_sdk;
    }

    public int getVersion_sdk_int() {
        return version_sdk_int;
    }

    public void setVersion_sdk_int(int version_sdk_int) {
        this.version_sdk_int = version_sdk_int;
    }

    public int getMaxCpuFreq() {
        return maxCpuFreq;
    }

    public void setMaxCpuFreq(int maxCpuFreq) {
        this.maxCpuFreq = maxCpuFreq;
    }

    public int getMinCpuFreq() {
        return minCpuFreq;
    }

    public void setMinCpuFreq(int minCpuFreq) {
        this.minCpuFreq = minCpuFreq;
    }

    public int getCurCpuFreq() {
        return curCpuFreq;
    }

    public void setCurCpuFreq(int curCpuFreq) {
        this.curCpuFreq = curCpuFreq;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getRam_total() {
        return ram_total;
    }

    public void setRam_total(String ram_total) {
        this.ram_total = ram_total;
    }

    public String getRam_free() {
        return ram_free;
    }

    public void setRam_free(String ram_free) {
        this.ram_free = ram_free;
    }

    public String getDisk_total() {
        return disk_total;
    }

    public void setDisk_total(String disk_total) {
        this.disk_total = disk_total;
    }

    public String getDisk_free() {
        return disk_free;
    }

    public void setDisk_free(String disk_free) {
        this.disk_free = disk_free;
    }

    public String[] getVersion() {
        return version;
    }

    public void setVersion(String[] version) {
        this.version = version;
    }

    public String[] getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String[] otherInfo) {
        this.otherInfo = otherInfo;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public boolean isCanDisableShutterSound() {
        return canDisableShutterSound;
    }

    public void setCanDisableShutterSound(boolean canDisableShutterSound) {
        this.canDisableShutterSound = canDisableShutterSound;
    }

    public String getCpu_temp() {
        return cpu_temp;
    }

    public void setCpu_temp(String cpu_temp) {
        this.cpu_temp = cpu_temp;
    }

    public boolean isNetavailable() {
        return netavailable;
    }

    public void setNetavailable(boolean netavailable) {
        this.netavailable = netavailable;
    }

    public boolean isGpsIsOpen() {
        return gpsIsOpen;
    }

    public void setGpsIsOpen(boolean gpsIsOpen) {
        this.gpsIsOpen = gpsIsOpen;
    }

    public boolean isWifiavailable() {
        return wifiavailable;
    }

    public void setWifiavailable(boolean wifiavailable) {
        this.wifiavailable = wifiavailable;
    }

    public boolean isSdCardIsExist() {
        return sdCardIsExist;
    }

    public void setSdCardIsExist(boolean sdCardIsExist) {
        this.sdCardIsExist = sdCardIsExist;
    }

    public boolean isCameraIsUse() {
        return cameraIsUse;
    }

    public void setCameraIsUse(boolean cameraIsUse) {
        this.cameraIsUse = cameraIsUse;
    }

    @Override
    public String toString() {
        return "Info{" +
                "deviceId='" + deviceId + '\'' +
                ", facsimileId='" + facsimileId + '\'' +
                ", board='" + board + '\'' +
                ", brand='" + brand + '\'' +
                ", cup_abi='" + cup_abi + '\'' +
                ", cup_abi2='" + cup_abi2 + '\'' +
                ", device='" + device + '\'' +
                ", display='" + display + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                ", host='" + host + '\'' +
                ", hardwareId='" + hardwareId + '\'' +
                ", hardware='" + hardware + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", product='" + product + '\'' +
                ", tags='" + tags + '\'' +
                ", time=" + time +
                ", type='" + type + '\'' +
                ", user='" + user + '\'' +
                ", bootloader='" + bootloader + '\'' +
                ", radio='" + radio + '\'' +
                ", version_release='" + version_release + '\'' +
                ", version_codename='" + version_codename + '\'' +
                ", version_incremental='" + version_incremental + '\'' +
                ", version_sdk='" + version_sdk + '\'' +
                ", version_sdk_int=" + version_sdk_int +
                ", maxCpuFreq=" + maxCpuFreq +
                ", minCpuFreq=" + minCpuFreq +
                ", curCpuFreq=" + curCpuFreq +
                ", cpuName='" + cpuName + '\'' +
                ", cpuUsage='" + cpuUsage + '\'' +
                ", ram_total='" + ram_total + '\'' +
                ", ram_free='" + ram_free + '\'' +
                ", disk_total='" + disk_total + '\'' +
                ", disk_free='" + disk_free + '\'' +
                ", version=" + Arrays.toString(version) +
                ", otherInfo=" + Arrays.toString(otherInfo) +
                ", currentVolume=" + currentVolume +
                ", city='" + city + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", density=" + density +
                ", brightness=" + brightness +
                ", orientation=" + orientation +
                ", facing=" + facing +
                ", canDisableShutterSound=" + canDisableShutterSound +
                ", cpu_temp='" + cpu_temp + '\'' +
                ", netavailable=" + netavailable +
                ", gpsIsOpen=" + gpsIsOpen +
                ", wifiavailable=" + wifiavailable +
                ", sdCardIsExist=" + sdCardIsExist +
                ", cameraIsUse=" + cameraIsUse +
                '}';
    }
}
