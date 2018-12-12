package cz.martinek.stepan.kayaklog.retrofit

open class HttpException(val code: Int, val msg: String): Exception(msg) {}

class RedirectException(code: Int, msg: String) : HttpException(code, msg) {}
class UnauthenticatedException(code: Int, msg: String) : HttpException(code, msg) {}
class ServerErrorException(code: Int, msg: String) : HttpException(code, msg) {}