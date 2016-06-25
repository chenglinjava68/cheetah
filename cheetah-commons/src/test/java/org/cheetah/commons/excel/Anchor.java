package org.cheetah.commons.excel;

import com.google.common.collect.Maps;
import org.cheetah.commons.excel.annotation.ExcelTitle;

import java.util.LinkedHashMap;

/**
 * 主播导出实体
 * 繁星ID、房间号、昵称、真实姓名、手机号
 * Created by maxhuang on 2016/6/23.
 */
public class Anchor {
    public final static String MOBILE_ANCHOR_TABLE = "手机直播素人主播表";
    public final static LinkedHashMap<String, String> headers = Maps.newLinkedHashMap();

    static {
//        繁星ID、房间号、昵称、真实姓名、手机号
        headers.put("繁星ID", Anchor.FXID);
        headers.put("房间号", Anchor.LIVEROOM);
        headers.put("昵称", Anchor.NICKNAME);
        headers.put("真实姓名", Anchor.NAME);
        headers.put("手机号", Anchor.PHONENUMBER);
    }

    public static final String FXID = "fxId";
    public static final String LIVEROOM = "liveRoom";
    public static final String NICKNAME = "nickname";
    public static final String NAME = "name";
    public static final String PHONENUMBER = "phoneNumber";

    private long fxId;
    private int liveRoom;
    private String nickname;
    private String name;
    private long phoneNumber;

    public Anchor() {

    }

    @ExcelTitle(order = 1, title = "繁星ID")
    public long getFxId() {
        return fxId;
    }

    public void setFxId(long fxId) {
        this.fxId = fxId;
    }

    @ExcelTitle(order = 2, title = "房间号")
    public int getLiveRoom() {
        return liveRoom;
    }

    public void setLiveRoom(int liveRoom) {
        this.liveRoom = liveRoom;
    }

    @ExcelTitle(order = 3, title = "昵称")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @ExcelTitle(order = 4, title = "真实姓名")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelTitle(order = 5, title = "手机号")
    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Anchor{" +
                "fxId=" + fxId +
                '}';
    }
}
