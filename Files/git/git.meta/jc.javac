public class git extends Component {
  @Order(idx = -2)
  public String linkNamePasth; // "name do usuario do github/ nome do repositorio -->  usuario/repositorio"

  @Order(idx = -1)
  public String pasth; // "caminho da pasta"

  @Order(idx = 1)
  public String Commit = "comito"; // "menssagem ou etiqueta da modificação do arquivo"

  @Order(idx = 2)
  public String toke; // "codigo toke do github"

  private String Dir;

  private gitClone gitclone = new gitClone();
  private gitPush gitpush = new gitPush();
  private gitPushMult gitpushmult = new gitPushMult();

  @Order(idx = 0)
  public PropertiesButton DownLoad =
      new PropertiesButton(
          new PropertiesButtonListener() {
            void onClicked() {
              DownLoad();
            }
          });

  @Order(idx = 3)
  public PropertiesButton UpLoad =
      new PropertiesButton(
          new PropertiesButtonListener() {
            void onClicked() {
              UpLoad();
            }
          });

  @Order(idx = 4)
  public PropertiesButton UpLoadAll =
      new PropertiesButton(
          new PropertiesButtonListener() {
            void onClicked() {
              UpLoadAll();
            }
          });

  public void DownLoad() {
    if (!verifica(false, false)) return;

    String DownloadUrl = "https://raw.githubusercontent.com/" + linkNamePasth + "/main/Files/" + pasth;
    gitclone.GitClone(DownloadUrl, Dir);

    StringBuilder InforDate = new StringBuilder();
    InforDate.append("{\n \"pasth\": \"").append(Dir).append("\",\n \"NameFile\": \"").append(pasth).append("\",\n \"Link\": \"").append(DownloadUrl).append("\"\n}");
    Console.log(InforDate.toString());
  }

  public void UpLoad() {
    if (!verifica(true, true)) return;

    String API_Url = "https://api.github.com/repos/" + linkNamePasth + "/contents/Files/" + pasth + "?ref=main";

    // busca o sha do file

    String shas = gitpush.getSha(API_Url, toke);
    gitpush.GitPush(API_Url, Commit, Dir, toke, shas);

    Console.log(!shas.isEmpty() ? "update" : "create");
    Console.log("Link: " + API_Url);
  }

  public void UpLoadAll() {
    if (!verifica(true, false)) return;
    File dir = new File(Dir);
    if (dir == null || !dir.exists()) return;

    // "lista todos oa file que existe"
    gitpushmult.UpVariaPasth(gitpush, dir, dir.getAbsolutePath() + "", linkNamePasth, Commit, pasth, toke);
  }

  public boolean verifica(boolean token, boolean pont) {
    if (!linkNamePasth.contains("/") || linkNamePasth.isEmpty()) {
      Toast.showText("esta errado o link do \"nome do usuario do git\" / nome do repositorio", 1);
      return false;
    }
    if (pasth == null || pasth.isEmpty() || (pont && !new File(Directories.getProjectFolder() + "/Files/" + pasth).isFile())) {
      Toast.showText("caminho para o arquivo esta faltando ou errado", 1);
      return false;
    } 
    if (token && (toke == null || toke.length() < 20)) {
      Toast.showText("o toke esta vazio ou faltando", 1);
      return false;
    }

    Dir = Directories.getProjectFolder() + "Files/" + pasth;
    return true;
  }
}
