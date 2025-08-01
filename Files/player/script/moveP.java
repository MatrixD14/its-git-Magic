public class moveP extends Component {
  private Vector2 slid = null;
  private menu stop;
  void start() {
    slid = Input.getAxisValue("move");
    stop = WorldController.findObject("cenario").findComponent("menu");
  }

  void repeat() {
     // if(stop.OpenPosh) return;
    moveObj();
  }

  private void moveObj() {
    Vector3 mypos = myObject.position;
    myObject.position.y = mypos.y > 2.12f ? 2.12f : mypos.y < -2.12f ? -2.12f : mypos.y;
    myObject.translate(0, -(slid.y / 100), 0);
  } 
}
