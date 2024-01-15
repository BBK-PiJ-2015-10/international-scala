package com.rs.service.external.dto

object Dto {

  trait Record {
    def status:String

    def source: String
  }

  case class OngoingRecord(status:String, id: String, source: String) extends Record

  case class FinishedRecord(status: String, source: String) extends Record

}
