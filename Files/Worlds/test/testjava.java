public class testjava extends Component {
  public ObjectFile inimigo;
  private float time = 0;
  public List<SpatialObject> inimig = new LinkedList<SpatialObject>();
  private int totalinimigo = 5, cont = 1;
  private menu stop;
  void start(){
      stop = WorldController.findObject("cenario").findComponent("menu");
  }
  void repeat() {
     // if(stop.OpenPosh) return;
    if (time < 1) time += 0.01f;
    spaw();
  }

  private void spaw() {
    SpatialObject spawI;
    if (inimig.size() < totalinimigo) {
      if (time > 0.25f && cont <100) {
        spawI = myObject.Instantiate(inimigo, new Vector3(-8, Random.range(-2.12f, 2.12f), 3.80f));
       // moveI destroy = new moveI(this, spawI);
        //spawI.addComponent(destroy);
        spawI.setName("inimig" + cont++);
        inimig.add(spawI);
        time=0;
      }
    } 
  }

  public class moveI extends Component {
    private float speed = 0.1f, time;
    private spaw list;
    private SpatialObject my;
    private menu stop;
    public moveI(spaw list, SpatialObject my) {
      this.list = list;
      this.my = my;
    }
    void start(){
        stop = WorldController.findObject("cenario").findComponent("menu");
    }
    void repeat() {
      //if (!my.exists() || stop.OpenPosh) return;
      if (time < 2) time += 0.01f;
      if (time > 1.5f) {
        list.inimig.remove(my);
        if (my.exists()) my.destroy();
        return;
      }
      move();
    }

    private void move() {
      myObject.translate(speed, 0, 0);
    }
  }
}

