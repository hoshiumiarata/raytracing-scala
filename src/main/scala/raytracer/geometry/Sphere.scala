package raytracer.geometry

import raytracer.mathematics.{Ray, Vector3}

class Sphere(val position: Vector3, val radius: Double) extends Geometry {
  override def normal(point: Vector3): Vector3 = (point - position).normalized

  override def intersect(ray: Ray): Option[Vector3] = {
    val a = ray.direction.dot(ray.direction)
    val b = 2.0 * ray.direction.dot(ray.initialPoint - position)
    val c = ray.initialPoint.dot(ray.initialPoint) + position.dot(position) - 2.0 * ray.initialPoint.dot(position) - radius * radius
    val d = b * b - 4 * a * c

    if (d < 0.0)
      return None

    val t1 = (-b + math.sqrt(d)) / (2.0 * a)
    val t2 = (-b - math.sqrt(d)) / (2.0 * a)

    if (t1 < 1e-5 && t2 < 1e-5)
      None
    else if (t2 < 1e-5)
      Some(ray.direction * t1 + ray.initialPoint)
    else if (t1 < 1e-5)
      Some(ray.direction * t2 + ray.initialPoint)
    else
      Some(ray.direction * math.min(t1, t2) + ray.initialPoint)
  }

  override def texCoord(point: Vector3): Vector3 = Vector3.zero
}
