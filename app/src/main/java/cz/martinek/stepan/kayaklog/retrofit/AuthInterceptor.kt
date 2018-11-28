package cz.martinek.stepan.kayaklog.retrofit

import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.url().toString().contains("/register"))
        {
            val base = Base64.encodeToString((ServerInfo.CLIENT_ID+":"+ServerInfo.CLIENT_SECRET).toByteArray(),Base64.DEFAULT).trim()
            request = request.newBuilder()
                .addHeader("Authorization","Basic "+ base)
                .build()
            return chain.proceed(request);
        }

        val token: String = "0JB52bpN7OS0Q48h2EaNonkWdJchOMDI"
        request = request.newBuilder()
                .addHeader("Authorization","Bearer "+token )
                .build()
        return chain.proceed(request)
    }
}