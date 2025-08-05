public class gitCloneMult {
  private gitClone gitclone;
  private String link, token;
  private File Dir;

  public void gitPasthAll(gitClone gitclone, File Dir, String link, String token) {
    this.Dir = Dir;
    this.gitclone = gitclone;
    this.link = link;
    this.token = token;
    new AsyncTask(
        new AsyncRunnable() {
          public Object onBackground(Object input) {
            processFile();
            return null;
          }

          public void onEngine(Object result) {
            Console.log("Download finalizado");
            Toast.showText("DownLoad the End", 1);
          }
        });
  }

  public void processFile() {
    try {
      HttpURLConnection com = (HttpURLConnection) new URL(link).openConnection();
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
      com.disconnect();

      FileJson(result.toString(),"");
    } catch (IOException e) {
      Console.log("erro no json: " + e.getMessage());
    }
  }

  public void FileJson(String jsons, String subPath) {
    try {
      GitCloneJson[] file = (GitCloneJson[]) Json.fromJson(jsons, GitCloneJson[].class, true);
      for (GitCloneJson json : file) {
        if (json.type.equals("file")) {
          File destino = new File(Dir, subPath + "/" + json.name);
          gitclone.GitClone(json.download_url, destino.getAbsolutePath());
          StringBuilder inforData = new StringBuilder();
          inforData.append("{\n \"Path\" : \"").append(Dir).append("\",\n \"File\" : \"").append(destino.getAbsolutePath()).append("\",\n \"Link\" : \"").append(json.url).append("\"\n}");
          Console.log(inforData.toString());
          Console.log(destino.exists()?"já existe sobrescrevel":"");
          
        } else if (json.type.equals("dir") || json.type.equals("directory")) {
            String pathAll = subPath + "/" + json.name;
            File path = new File(Dir, pathAll);
            if(!path.exists()) path.mkdirs();
            
          try {
            HttpURLConnection com = (HttpURLConnection) new URL(json.url).openConnection();
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
            com.disconnect();

            FileJson(result.toString(),pathAll);
          } catch (Exception e) {
              Console.log("erro ao acessar a path: "+subPath+ " -->" + e.getMessage());
          } 
        }
      }
    } catch (Exception e) {
      Console.log("erro no Download file: " + e.getMessage());
    }
  }

  public static class GitCloneJson {
    public String name;
    public String url;
    public String download_url;
    public String type;
    public String sha;
  }
}
