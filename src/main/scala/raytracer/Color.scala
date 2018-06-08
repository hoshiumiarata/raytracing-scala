package raytracer


class Color(val red: Double, val green: Double, val blue: Double) {
  def +(that: Color) = new Color(red + that.red, green + that.green, blue + that.blue)
  def *(that: Double) = new Color(red * that, green * that, blue * that)
  def *(that: Color) = new Color(red * that.red, green * that.green, blue * that.blue)

  def clamped = new Color(0.0 max red min 1.0, 0.0 max green min 1.0, 0.0 max blue min 1.0)
}

object Color {
  def black = new Color(0, 0, 0)
  def white = new Color(1, 1, 1)
}