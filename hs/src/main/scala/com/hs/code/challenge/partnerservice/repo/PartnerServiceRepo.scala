package com.hs.code.challenge.partnerservice.repo

import com.hs.code.challenge.partnerservice.entities.ApiEntities.{Partner, Partners}

trait PartnerServiceRepo {

  def fetchPartners(): Partners

}


case class PartnerServiceRepoImpl() extends PartnerServiceRepo {
  override def fetchPartners(): Partners = ???

}
