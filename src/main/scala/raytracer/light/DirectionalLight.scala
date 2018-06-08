package raytracer.light

import raytracer.Color
import raytracer.mathematics.Vector3


class DirectionalLight(val direction: Vector3, ambientColor: Color, diffuseColor: Color, specularColor: Color) extends Light(ambientColor, diffuseColor, specularColor) {

}
