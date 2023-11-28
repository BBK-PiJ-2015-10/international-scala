package com.hs.code.challenge.dataservice

import com.hs.code.challenge.dataservice.api.DataWebService
import zio.ZIOAppDefault
import zio._
import zio.http.Server

object DataServiceAppRunner extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    Server.serve(DataWebService.app).provide(Server.defaultWithPort(9080))

}
