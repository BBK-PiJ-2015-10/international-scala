package codi.deutsche

class PointsOverlapImpl extends PointsOverlap {

  override def maxSpread(x: Array[Int], y: Array[Int]): Int = ???




  private def evalutedOverlap(points: List[Point], start: Int) = {
    val fucker : Option[Boolean] = None
    val head = points.head

    val cat = List(1,2,3)

    cat.foldLeft(0)((a,b)=> a +b)


    points.foldLeft(fucker)((a,b) => PointAnalyzer.overlap(start,b,a))




  }



}
