package raytracer.mathematics

class Vector3(val x: Double, val y: Double, val z: Double) {
  def unary_- = new Vector3(-x, -y, -z)
  def +(that: Vector3) = new Vector3(x + that.x, y + that.y, z + that.z)
  def -(that: Vector3) = new Vector3(x - that.x, y - that.y, z - that.z)
  def *(that: Double) = new Vector3(x * that, y * that, z * that)
  def length: Double = math.sqrt(x * x + y * y + z * z)
  def distanceTo(that: Vector3) = (this - that).length
  def dot(that: Vector3) = x * that.x + y * that.y + z * that.z
  def reflect(normal: Vector3) = this - normal * 2 * dot(normal)

  def normalized = {
    val len = length
    if (len > 0)
      new Vector3(x / len, y / len, z / len)
    else
      Vector3.zero
  }
}

object Vector3 {
  def zero = new Vector3(0, 0, 0)
  def ones = new Vector3(1, 1, 1)
}
