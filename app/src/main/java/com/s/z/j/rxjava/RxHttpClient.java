package com.s.z.j.rxjava;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Http请求
 * Created by Administrator on 2015/11/15.
 */
public class RxHttpClient {
    private static final String BASE_URL = "http://192.168.88.243:8080/SzjTest/";//API接口地址公共部分
    Api api = null;
    private static RxHttpClient instanse;

    public RxHttpClient() {
    }

    public static RxHttpClient getInstance() {
        if (instanse == null) {
            instanse = new RxHttpClient();
        }
        return instanse;
    }

    public void init(final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    interface Api {

        @POST("getUserInfo")//获取用户信息
        @FormUrlEncoded
            //post请求要加这句
        Call<User> getUserInfo(@Field("id") int id);

        @POST("login")//登陆
        @FormUrlEncoded
            //post请求要加这句
        Call<BaseResult> login(@Field("username") String username, @Field("password") String password);

    }
}


//接口
//@FormUrlEncoded   post请求
//@Multipart        上传文件
//@Field            post使用
//@Query            get使用
//    interface Api {
//        //        get  示例----后面要删除
////        @GET("/twitter/index")
////        void twitterList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<TwitterResp> cb);
//
//        @POST("/oauth/access_token")//登陆
//        @FormUrlEncoded
//            //post请求要加这句
//        void login(@Field("grant_type") String grant_type, @Field("client_id") String client_id, @Field("client_secret") String client_secret, @Field("username") String username, @Field("password") String password, Callback<Object> callback);

//        @POST("/common/upload")//上传图片，
//        @Multipart
//这里上传图片用 @Multipart，参数用 @Part 和正常的访问方法不一样
//        void uploadFile(@Part("access_token") String access_token, @Part("file") TypedFile file, Callback<UploadResp> callback);

//        @POST("/users/changePassword")//修改密码
//        @FormUrlEncoded
//        void changePassword(@Field("access_token") String access_token, @Field("old_password") String old_password, @Field("password") String password, @Field("password_confirmation") String password_confirmation, Callback<Resp> callback);
//
//        @POST("/users/register")//新用户注册
//        @FormUrlEncoded
//        void register(@Field("email") String email, @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("password") String password, @Field("password_confirmation") String password_confirmation, Callback<RegisterResp> callback);

