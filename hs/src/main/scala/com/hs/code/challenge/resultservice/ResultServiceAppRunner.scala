package com.hs.code.challenge.resultservice

import com.hs.code.challenge.resultservice.api.ResultWebService
import zio.ZIOAppDefault
import zio._
import zio.http.Server

object ResultServiceAppRunner extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    Server.serve(ResultWebService.app).provide(Server.defaultWithPort(9080))

}
