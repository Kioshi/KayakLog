package cz.martinek.stepan.kayaklog.retrofit

import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.url().toString().contains("/register") || request.url().toString().contains("/auth/token"))
        {
            val base = Base64.encodeToString((ServerInfo.CLIENT_ID+":"+ServerInfo.CLIENT_SECRET).toByteArray(),Base64.DEFAULT).trim()
            request = request.newBuilder()
                .addHeader("Authorization","Basic "+ base)
                .build()
            return chain.proceed(request);
        }

        request = request.newBuilder()
                .addHeader("Authorization","Bearer "+ServerInfo.authData?.access_token ?: "")
                .build()
        return chain.proceed(request)
    }
}