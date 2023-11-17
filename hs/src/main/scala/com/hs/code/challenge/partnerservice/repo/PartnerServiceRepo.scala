package com.hs.code.challenge.partnerservice.repo

import com.hs.code.challenge.partnerservice.entities.ApiEntities.Partners
import zio.{ULayer, ZIO, ZLayer}

trait PartnerServiceRepo {

  def fetchPartners(): ZIO[Any,Throwable,Partners]

}


case class PartnerServiceRepoImpl() extends PartnerServiceRepo {
  override def fetchPartners()  = DataFetcherUtils.fetchPartnersData()

}

object PartnerServiceRepo {

  val layer: ULayer[PartnerServiceRepoImpl] = ZLayer.succeed(PartnerServiceRepoImpl())

}
