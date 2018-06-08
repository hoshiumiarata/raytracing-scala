package raytracer.geometry

import raytracer.mathematics.{Ray, Vector3}

class Plane(val position: Vector3, val normal: Vector3) extends Geometry {
  override def normal(point: Vector3): Vector3 = normal
  override def intersect(ray: Ray): Option[Vector3] = {
    val k = normal.dot(ray.direction)
    if (k.abs < 1e-5)
      return None

    val b = normal.dot(ray.initialPoint - position)
    val t = -b / k

    if (t < 1e-5)
      None
    else
      Some(ray.direction * t + ray.initialPoint)
  }
  override def texCoord(point: Vector3): Vector3 = Vector3.zero
}
