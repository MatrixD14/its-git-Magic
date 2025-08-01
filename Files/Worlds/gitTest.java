public class gitTest extends Component {
  @Order(idx = -2)
  public String linkNamePasth; // "name do usuario do github/ nome do repositorio -->  usuario/repositorio"

  @Order(idx = -1)
  public String pasth; // caminho da pasta

  @Order(idx = 1)
  public String Commit = "comito"; // menssagem ou etiqueta da modificação do arquivo

  @Order(idx = 2)
  public String toke; // codigo toke do github

  private String Dir;

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

  public boolean verifica(boolean token) {
    if (!linkNamePasth.contains("/") || linkNamePasth.isEmpty()) {
      Toast.showText("esta errado o link do \"nome do usuario do git\" / nome do repositorio", 1);
      return false;
    }
    if (pasth == null || pasth.isEmpty() || !pasth.contains(".")) {
      Toast.showText("caminho para o arquivo esta faltando ou errado", 1);
      return false;
    }
    if (token && (toke == null || toke.length() < 20)) {
      Toast.showText("o toke esta vazio ou faltando", 1);
      return false;
    }

    Dir = Directories.getProjectFolder() + "/Files/" + pasth;
    return true;
  }

  public void DownLoad() {
    if (!verifica(false)) return;

    String DownloadUrl = "https://raw.githubusercontent.com/" + linkNamePasth + "/main/Files/" + pasth;
    // GitClone(DownloadUrl, Dir);

    StringBuilder InforDate = new StringBuilder();
    // InforDate.append("{\n \"pasth\": \"").append(Dir).append("\",\n \"NameFile\": \"").append(pasth).append("\",\n \"Link\": \"").append(DownloadUrl).append("\"\n}");
    // Console.log(InforDate.toString());
  }

  public void UpLoad() {
    if (!verifica(true)) return;

    String API_Url = "https://api.github.com/repos/" + linkNamePasth + "/contents/Files/" + pasth + "?ref=main";

    // busca o sha do file

    String shas = getSha(API_Url, toke);
    GitPush(API_Url, Commit, Dir, toke, shas);

    Console.log(!shas.isEmpty() ? "update" : "create");
    Console.log("Link: " + API_Url);
  }

  public void UpLoadAll() {
    if (!verifica(true)) return;
    File dir = new File(Directories.getProjectFolder() + "/Files/");
    if (dir == null || !dir.exists()) return;
    File[] file = dir.listFiles();
    if (file == null || file.length == 0) return;

    for (File f : file) {

      if (f.isDirectory() || f.getName().startsWith(".")) continue;
      String name = f.getName();
      String cominho = f.getAbsolutePath();
      String API_Url = "https://api.github.com/repos/" + linkNamePasth + "/contents/Files/" + name + "?ref=main";

      // busca o sha do file

      String shas = getSha(API_Url, toke);
      GitPush(API_Url, Commit, cominho, toke, shas);

      Console.log(!shas.isEmpty() ? "update" : "create");
      Console.log("Link: " + API_Url);
    }
  } 

  public void GitClone(String link, String path) {
    try {
      URL url = new URL(link);
      InputStream in = url.openStream();
      BufferedInputStream bs = new BufferedInputStream(in);
      FileOutputStream fs = new FileOutputStream(path);
      byte[] date = new byte[1024];
      int count;
      while ((count = bs.read(date, 0, 1024)) != -1) {
        fs.write(date, 0, count);
      }
      fs.flush();
      fs.close();
      bs.close();
      in.close();
    } catch (Exception e) {
      Console.log(e);
    }
  }

  public void GitPush(String link, String menssage, String pasth, String token, String sha) {
    try {
      byte[] date = readFile(pasth);
      String encode = Base64.getEncoder().encodeToString(date);

      // json que sera enviado para api.github

      StringBuilder json = new StringBuilder();
      json.append("{\n  \"message\": \"").append(menssage).append("\",\n  \"content\": \"").append(encode).append("\",\n  \"branch\": \"main\"");
      if (sha != null && !sha.isEmpty()) json.append(",\n  \"sha\": \"").append(sha).append("\"");
      json.append("\n}");
      Console.log(json.toString());

      URL url = new URL(link);

      // connect com o github

      HttpURLConnection com = (HttpURLConnection) url.openConnection();
      com.setRequestMethod("PUT");
      com.setDoOutput(true);
      com.setRequestProperty("Authorization", "token " + token);
      com.setRequestProperty("Content-Type", "application/json");

      OutputStream output = com.getOutputStream();
      output.write(json.toString().getBytes("UTF-8"));
      output.flush();
      output.close();

      /// vierifica se teve algum erro ao conectar como github

      int menss = com.getResponseCode();
      Console.log(menss == 201 || menss == 200 ? "file enviado sucess" : "erro em algum folder");

      InputStream input = (menss >= 400) ? com.getErrorStream() : com.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      StringBuilder result = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) result.append(line);
      Console.log("\nreposta: " + result.toString());
      input.close();
    } catch (Exception e) {
      Console.log(e);
    }
  }

  /// função que ve o tamanho do file que sera enviado para git para não manda faltando em byte

  public byte[] readFile(String pasth) {
    try {
      FileInputStream fs = new FileInputStream(pasth);
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] date = new byte[1024];
      int read;
      while ((read = fs.read(date)) != -1) buffer.write(date, 0, read);
      fs.close();
      return buffer.toByteArray();
    } catch (Exception e) {
      Console.log(e);
    }
    return null;
  }

  /// funcão vai busca no json da api.github onde esta o "sha" do file existe no github

  public String getSha(String link, String token) {
    try {
      URL url = new URL(link);

      HttpURLConnection com = (HttpURLConnection) url.openConnection();
      com.setRequestMethod("GET");
      com.setRequestProperty("Authorization", "token " + token);
      com.setRequestProperty("Accept", "application/vnd.github.v3+json");

      int menss = com.getResponseCode();
      InputStream input = (menss >= 400) ? com.getErrorStream() : com.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      StringBuilder result = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) result.append(line);
      input.close();

      GitJson json = (GitJson) Json.fromJson(result.toString(), GitJson.class, true);

      if (json != null && json.sha != null) return json.sha;
      else Console.log("falho o sha");
      input.close();
    } catch (Exception e) {
      Console.log(e);
    }
    return "";
  }

  public static class GitJson {
    public String sha;
  }
}
