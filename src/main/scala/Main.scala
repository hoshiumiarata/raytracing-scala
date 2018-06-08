import javax.swing.{ImageIcon, JLabel, JOptionPane}
import raytracer.{Color, Entity, Raytracer}
import raytracer.geometry.{Plane, Sphere}
import raytracer.light.DirectionalLight
import raytracer.material.ColorMaterial
import raytracer.mathematics.Vector3


object Main extends App {
  val raytracer = new Raytracer(512, 512)
  raytracer.objects += new Entity(
    new Plane(new Vector3(0, -2, 0), new Vector3(0, 1, 0)),
    new ColorMaterial(new Color(0, 0, 1), 0.4, 0.8, 1.0, 0, 0.1))
  raytracer.objects += new Entity(
    new Plane(new Vector3(0, 0, -20), new Vector3(0, 0, 1)),
    new ColorMaterial(new Color(0, 1, 0), 0.4, 0.8, 1.0, 0, 0.0))

  raytracer.objects += new Entity(
    new Sphere(new Vector3(0, 3, -15), 3),
    new ColorMaterial(new Color(1, 0, 0), 0.4, 0.8, 1.0, 2, 0.0))
  raytracer.lights += new DirectionalLight(new Vector3(1, -1, -1).normalized, Color.white, Color.white, Color.white)
  val t0 = System.currentTimeMillis()
  val frame = raytracer.render()
  val t1 = System.currentTimeMillis()
  System.out.println(t1 - t0)
  JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(frame)))
}
