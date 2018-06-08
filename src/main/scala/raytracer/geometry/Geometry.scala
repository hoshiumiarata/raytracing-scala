package raytracer.geometry

import raytracer.mathematics.{Ray, Vector3}

abstract class Geometry {
  def normal(point: Vector3): Vector3
  def intersect(ray: Ray): Option[Vector3]
  def texCoord(point: Vector3): Vector3
}
