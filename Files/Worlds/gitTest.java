public class gitTest extends Component {
  @Order(idx = -2)
  public String linkNamePasth, paths;
  public String Commit = "comito", toke;
  private String Dir;
  
  @Order(idx = -1)
  public PropertiesButton DownLoad =
      new PropertiesButton(
          new PropertiesButtonListener() {
            void onClicked() {
              download();
            }
          });

  public PropertiesButton update =
      new PropertiesButton(
          new PropertiesButtonListener() {
            void onClicked() {
              upFile();
            }
          });

  public void download() {
   Dir = Directories.getProjectFolder() + "/Files/" + paths;
    String DownloadUrl = "https://raw.githubusercontent.com/"+linkNamePasth+"/main/Files/"+paths;    
    update(DownloadUrl, Dir);
    
    String InforDate = "{\n \"pasth\": \"" + Dir + "\",\n \"NameFile\": \"" + paths + "\"Link\": \""+DownloadUrl+"\n}";
    Console.log(InforDate);
  }

  public void upFile() {
    Dir = Directories.getProjectFolder() + "/Files/" + paths;
    String API_Url = "https://api.github.com/repos/"+linkNamePasth+"/contents/Files/" + paths + "?ref=main";
    //busca o sha do file
    String shas = getSha(API_Url, toke);
    GitPush(API_Url, Commit, Dir, toke, shas);
    
    Console.log(!shas.isEmpty() ? "update" : "create");
    Console.log("Link: "+API_Url);
  } 

  public void update(String link, String path) {
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
      String json = "{\n  \"message\": \"" + menssage + "\",\n  \"content\": \"" + encode + "\",\n  \"branch\": \"main\""+ (sha!= null && !sha.isEmpty()?",\n  \"sha\": \"" + sha + "\"":"")+"\n}";
      Console.log(json);

      URL url = new URL(link);
      HttpURLConnection com = (HttpURLConnection) url.openConnection();
      com.setRequestMethod("PUT");
      com.setDoOutput(true);
      com.setRequestProperty("Authorization", "token " + token);
      com.setRequestProperty("Content-Type", "application/json");

      OutputStream output = com.getOutputStream();
      output.write(json.getBytes("UTF-8"));
      output.flush();
      output.close();
      int menss = com.getResponseCode();
      Console.log(menss == 201 || menss == 200 ? "file enviado sucess" : "erro em algum folder");

      InputStream input = (menss >= 400) ? com.getErrorStream() : com.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      StringBuilder result = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) result.append(line);
      Console.log("\nreposta: " + result.toString());
    } catch (Exception e) {
      Console.log(e);
    }
  }

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
            
      GitJson json = (GitJson)Json.fromJson(result.toString(), GitJson.class,true);
      
      if(json != null && json.sha != null)return json.sha;
      else Console.log("falho o sha");
    } catch (Exception e) {
      Console.log(e);
    }
    return "";
  }
  public class GitJson{
      public String sha;
  }
}
