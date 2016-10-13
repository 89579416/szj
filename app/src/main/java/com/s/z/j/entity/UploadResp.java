package com.s.z.j.entity;

/**
 * 上传图片返回
 * 根据实际情况修改
 * Created by Administrator on 2016/4/28 0028.
 */
public class UploadResp {
    private int ret;
    private boolean is_valid;
    private String uri;

    public UploadResp() {
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public boolean is_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "UploadResp{" +
                "ret=" + ret +
                ", is_valid=" + is_valid +
                ", uri='" + uri + '\'' +
                '}';
    }
}
