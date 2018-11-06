package com.frate.luxup

const val BASE_WEB_URL = "http://10.0.2.2:8080"
const val CLOUD_STORAGE_BUCKET = "gs://blablabla.appspot.com"
val API_TIME_OUT = if (BuildConfig.DEBUG) 60L else 20L
