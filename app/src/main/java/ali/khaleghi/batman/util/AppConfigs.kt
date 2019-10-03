package ali.khaleghi.batman.util

object AppConfigs {

    const val BASE_URL = "http://omdbapi.com"

    const val TAG = "Batman"

    const val HTTP_CONNECTION_TIMEOUT : Long = 30 //seconds
    const val HTTP_READ_TIMEOUT       : Long = 30 //seconds
    const val HTTP_WRITE_TIMEOUT      : Long = 30 //seconds

    const val CACHE_SIZE: Int        = 10     //MegaBytes
    const val CACHE_EXPIRE_TIME: Int = 24*30  //hours

}
