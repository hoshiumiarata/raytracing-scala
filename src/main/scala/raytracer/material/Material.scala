package raytracer.material

import raytracer.Color
import raytracer.mathematics.Vector3


abstract class Material(val ambient: Double, val diffuse: Double, val specular: Double, val shininess: Double, val reflection: Double) {
  def colorAt(texCoord: Vector3): Color
}
