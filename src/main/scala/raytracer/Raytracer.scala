package raytracer

import java.awt.image.{BufferedImage, DataBufferByte}

import raytracer.light.{DirectionalLight, Light}
import raytracer.mathematics.{Ray, Vector3}

import scala.collection.mutable


class Raytracer(width: Int, height: Int) {
  val Focus = math.sqrt(3)
  val ReflectionPowerLimit = 1e-5
  val sceneAmbient = new Color(0.1, 0.1, 0.1)
  val objects = new mutable.Queue[Entity]
  val lights = new mutable.Queue[Light]
  def render(): BufferedImage = {
    val frame = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)
    val pixels = frame.getRaster.getDataBuffer.asInstanceOf[DataBufferByte].getData
    for (i <- (0 until width * height).par; x = i % width; y = i / width) {
      val pixelColor = pixel(x, y)
      pixels(i * 3)     = math.round(pixelColor.blue  * 255).toByte
      pixels(i * 3 + 1) = math.round(pixelColor.green * 255).toByte
      pixels(i * 3 + 2) = math.round(pixelColor.red   * 255).toByte
    }
    frame
  }

  def trace(ray: Ray): Option[TracingResult] = {
    var result: Option[TracingResult] = None
    for (obj <- objects) {
      obj.geometry.intersect(ray) match {
        case Some(intersectionPoint) =>
          result match {
            case Some(TracingResult(_, lastPosition)) =>
              if (intersectionPoint.distanceTo(ray.initialPoint) < lastPosition.distanceTo(ray.initialPoint))
                result = Some(TracingResult(obj, intersectionPoint))
            case None =>
              result = Some(TracingResult(obj, intersectionPoint))
          }
        case _ =>
      }
    }
    result
  }

  def pixel(x: Int, y: Int): Color = {
    color(new Ray(Vector3.zero, new Vector3(2.0 * x / width - 1.0, 1.0 - 2.0 * y / height, -Focus))).clamped
  }

  def color(ray: Ray): Color = {
    if (ray.power < ReflectionPowerLimit)
      return Color.black

    trace(ray) match {
      case Some(TracingResult(entity, position)) =>
        var i = sceneAmbient
        val n = entity.geometry.normal(position)
        for (light <- lights) {
          light match {
            case directionalLight: DirectionalLight =>
              trace(new Ray(position, -directionalLight.direction)) match {
                case None =>
                  val ambient = light.ambientColor * entity.material.ambient
                  val diffuseCoef = n.dot(-directionalLight.direction) max 0
                  val diffuse = light.diffuseColor * (entity.material.diffuse * diffuseCoef)
                  var specularCoef = directionalLight.direction.reflect(n).dot(-ray.direction) max 0
                  if (specularCoef > 0)
                    specularCoef = Math.pow(specularCoef, entity.material.shininess)
                  val specular = light.specularColor * (entity.material.specular * specularCoef)
                  i = i + ambient + diffuse + specular
                case Some(_) =>
              }
          }
        }

        val refl = color(new Ray(position, ray.direction.reflect(n), ray.power * entity.material.reflection)).clamped
        (entity.material.colorAt(entity.geometry.texCoord(position)) * i + refl).clamped * ray.power
      case None =>
        Color.black
    }
  }
}
