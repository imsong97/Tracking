package com.yunho.tracking.data.api

import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class TrackingAPI {

    private val url = "http://img.sweettracker.net/image/mobile_test/"

    // 생성자로 처리, api 메서드 별도
    private var retrofit: Retrofit = Retrofit.Builder()
                                        .baseUrl(url)
                                        .addConverterFactory(GsonConverterFactory.create()) // 서버로부터 데이터를 받고 원하는 타입으로 바꾸기 위해
                                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava와 함께 사용 시 필수, Observable형태로 바꿔줌
                                        .build()

    var api: API = retrofit.create(API::class.java) // network request

    // companion object -> class의 인스턴스 없이 접근할 수 있도록 함
    companion object {
        @Volatile // 접근 가능한 변수의 값을 캐시를 통해 사용하지 않고 스레드가 직접 main memory에 접근하여 읽고 씀 -> 동기화의 이유
        private var mInstance: TrackingAPI? = null

        val instance: TrackingAPI?
            get() = synchronized(TrackingAPI::class.java) { // synchronized -> thread-safe
                if (mInstance == null) { // null 체크 -> 성능저하 완화
                    mInstance = TrackingAPI()
                }
                return mInstance
            }
    }

    interface API{
        @GET("mobile.json")
        fun getTrackingData(): Single<Response<TrackingDataEntity>>
    }
}