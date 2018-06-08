package raytracer.material
import raytracer.Color
import raytracer.mathematics.Vector3


class ColorMaterial(color: Color, ambient: Double, diffuse: Double, specular: Double, shininess: Double, reflection: Double) extends Material(ambient, diffuse, specular, shininess, reflection) {
  override def colorAt(texCoord: Vector3): Color = color
}
