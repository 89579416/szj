package com.s.z.j.net;

import android.content.Context;
import android.util.Log;

import com.s.z.j.entity.RegisterResp;
import com.s.z.j.entity.Resp;
import com.s.z.j.entity.UploadResp;
import com.squareup.okhttp.OkHttpClient;
import com.szj.library.utils.L;
import com.szj.library.utils.T;

import org.xutils.DbManager;

import java.io.File;
import java.net.CookieManager;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Http请求
 * Created by Administrator on 2015/11/15.
 */
public class HttpClient {
    public static final String OSS_URL = "http://facsimile-assets.oss-cn-hangzhou.aliyuncs.com/";//阿里云访问地址前面部分
    private static final String BASE_URL = "https://api.facsimilemedia.com";//API接口地址公共部分
    public static final  String updateUrl = "http://dl.facsimilemedia.com/update/response/updates.xml";//更新下载地址
    private Context mContext;
    private RestAdapter restAdapter = null;
    private NetInterface netInterface = null;

    private static HttpClient instanse;
    static DbManager db;
    public HttpClient() {
    }

    public static HttpClient getInstance() {
        if (instanse == null) {
            instanse = new HttpClient();
        }
        return instanse;
    }


    public static void setDb(DbManager dbManager) {
        db = dbManager;
    }

    public void init(final Context context){
        mContext = context;

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(new OkHttpClient().setCookieHandler(new CookieManager())))
                .setLogLevel(RestAdapter.LogLevel.FULL)//打印所有日志
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
//                        request.addHeader("Authorization", authorization);//如果要添加请求头
                    }
                })
                .build();

        netInterface = restAdapter.create(NetInterface.class);
    }

    //接口
    //@FormUrlEncoded   post请求
    //@Multipart        上传文件
    //@Field            post使用
    //@Query            get使用
    interface NetInterface {
        //        get  示例----后面要删除
//        @GET("/twitter/index")
//        void twitterList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<TwitterResp> cb);

        @POST("/oauth/access_token")//登陆
        @FormUrlEncoded     //post请求要加这句
        void login(@Field("grant_type") String grant_type, @Field("client_id") String client_id, @Field("client_secret") String client_secret, @Field("username") String username, @Field("password") String password, Callback<Object> callback);

        @POST("/common/upload")//上传图片，
        @Multipart//这里上传图片用 @Multipart，参数用 @Part 和正常的访问方法不一样
        void uploadFile(@Part("access_token") String access_token, @Part("file") TypedFile file, Callback<UploadResp> callback);

        @POST("/users/changePassword")//修改密码
        @FormUrlEncoded
        void changePassword(@Field("access_token") String access_token, @Field("old_password") String old_password, @Field("password") String password, @Field("password_confirmation") String password_confirmation, Callback<Resp> callback);

        @POST("/users/register")//新用户注册
        @FormUrlEncoded
        void register(@Field("email") String email, @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("password") String password, @Field("password_confirmation") String password_confirmation, Callback<RegisterResp> callback);

    }

    /**
     * 上传图片，
     * @param access_token  访问令牌
     * @param file  文件
     * @param callback
     */
    public void uploadFile(String access_token,File file,Callback<UploadResp> callback){
        if(isNetinterFace()){
            if(file.exists()) {
                L.i("文件存在，" + file.getPath());
                TypedFile typedFile = new TypedFile("image/jpg", file);
                netInterface.uploadFile(access_token, typedFile, callback);
            }else{
                L.i("文件不存在" + file.getPath());
                T.s(mContext, "文件不存在");
            }
        }
    }

    /**
     * 登录
     * XKxcExlpWn54s8Tm
     * u9xePDZht5Hc7oAoFORlUpvjD1eAAv3K
     * 上面是我申请的下面是测试用的
     * chusfl8wz1LAI1R1
     * hx1xPMcBJq6HGlNvICZJRB1AEDKeXus4
     * @param username   账户名
     * @param password  密码
     * @param callback  回调
     */
    public void login(String username, String password, Callback<Object> callback) {
        if(isNetinterFace()){
            netInterface.login("password", "XKxcExlpWn54s8Tm", "u9xePDZht5Hc7oAoFORlUpvjD1eAAv3K", username, password, callback);
        }
    }

    /**
     * 新用户注册
     * @param email 邮箱
     * @param first_name    姓
     * @param last_name 名
     * @param password  密码
     * @param password_confirmation  密码确认
     * @param callback
     */
    public void register(String email,String first_name,String last_name,String password,String password_confirmation,Callback<RegisterResp>callback){
        if(isNetinterFace()){
            netInterface.register(email, first_name, last_name,password,password_confirmation,callback);
        }
    }

    /**
     * 修改密码
     * @param access_token
     * @param old_password  原密码
     * @param password  新密码
     * @param password_confirmation 新密码
     * @param callback
     */
    public void changePassword(String access_token,String old_password,String password,String password_confirmation,Callback<Resp>callback){
        if(isNetinterFace()){
            Log.i("AAAA","changePasswor开始 ");
            netInterface.changePassword(access_token,old_password, password,password_confirmation,callback);
        }
    }
    /**
     * netInterface 是否存在
     * @return
     */
    public boolean  isNetinterFace(){
        if(netInterface!=null){
            return true;
        }else {
            Log.i("AAAA","netInterface==null");
            return false;
        }
    }

}
