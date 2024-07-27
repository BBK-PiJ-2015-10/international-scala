package codi.deutsche

object PointAnalyzer {

  def overlap(distance: Int, a: Point, b: Point): Option[Boolean] = {
    val xDelta = Math.abs(a.x - b.x)
    val yDelta = Math.abs(a.y - b.y)
    val largestDelta = Math.max(xDelta, yDelta)
    val doubleDistance = distance * 2
    val diff = largestDelta - doubleDistance
    diff match {
      case _ == 0 => Some(false)
      case _ > 0 => None
      case _ < 0 => Some(true)
    }
  }

}
