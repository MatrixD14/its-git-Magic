public class atiraP extends Component {
  private float time = 0;
  private SUIRect scale = new SUIRect();
  public ObjectFile hole;
  private List<SpatialObject> balas = new LinkedList<SpatialObject>();
  private SpatialObject shot;
  private menu stop;

  void start() {
    balas.clear();
    stop = WorldController.findObject("cenario").findComponent("menu");
    scale = WorldController.findObject("atira").findComponent("suirect");
    shot = WorldController.findObject("shot");
    dupbala();
  } 

  void repeat() {
    if (stop.OpenPosh) return;
    if (time < 1) time += 0.01f;
    atira();
  }

  private void atira() {
    if (Input.isKeyDown("atira") || Input.keyboard.isKeyDown("space")) {
      scale.setInt("Height", 160);
      if (time > 0.02f) {
        reloardBalaActive();
        time = 0;
      }
    } else scale.setInt("Height", 150);
  }

  private void dupbala() {
    SpatialObject bala;
    for (int value = 1; value < 12; ++value) {
      bala = myObject.Instantiate(hole);
      bala.addComponent(new moves());
      bala.setName("bala" + value);
      bala.setEnabled(false);
      balas.add(bala);
    }
  }

  private void reloardBalaActive() {
    SpatialObject bala;
    boolean onoff = false;
    for (int list = 0; list < balas.size(); ++list) {
      if (!onoff) {
        bala = balas.get(list);
        if (!bala.isEnabled()) {
          bala.setEnabled(true);
          bala.position = shot.getGlobalPosition();
          onoff = true;
          break;
        }
      }
    }
    if (!onoff) {
      bala = balas.get(0);
      bala.position = shot.getGlobalPosition();
      bala.setEnabled(true);
    }
  }

  public class moves extends Component {
    private float speed = 10, time = 0;

    void repeat() {
      if (time < 1) time += 0.01f;
      if (time > 0.3f) {
        myObject.setEnabled(false);
        time = 0;
      }
      mov();
    }

    public void mov() {
      myObject.moveInSeconds(-speed, 0, 0);
    }
  }
}
