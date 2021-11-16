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
    private var retrofit: Retrofit
    var api: API

    init { // 인스턴스 생성 시 가장 먼저 호출, 기본생성자 호출 시 바로 실행
        retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()) // 서버로부터 데이터를 받고 원하는 타입으로 바꾸기 위해
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava와 함께 사용 시 필수, Observable형태로 바꿔줌
                    .build()

        api = retrofit.create(API::class.java) // network request
    }
    // constructor{}: 보조 생성자(부 생성자), init 다음으로 실행

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