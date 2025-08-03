public class gitCloneMult {
  private gitClone gitclone;
  private String link, path, token;
  private File Dir;
  public void gitPasthAll(gitClone gitclone, File Dir,String link, String path, String token) {
      this.Dir = Dir;
    this.gitclone = gitclone;
    this.link = link;
    this.path = path;
    this.token = token;
    new AsyncTask(
        new AsyncRunnable() {
          public Object onBackground(Object input) {
            processFile();
            return null;
          }

          public void onEngine(Object result) {}
        });
  }

  public void processFile() {
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
      com.disconnect();

      FileJson(result.toString());
    } catch (IOException e) {
    }
  }

  public void FileJson(String jsons) {
    if (jsons.startsWith("[")) jsons = jsons.substring(1);
    if (jsons.endsWith("]")) jsons = jsons.substring(0, jsons.length() - 1);

    String[] file = jsons.split("\\},\\{");
    for (int i = 0; i < file.length; i++) {
      String obj = file[i];
      if (!obj.startsWith("{")) obj = "{" + obj;
      if (!obj.endsWith("{")) obj = obj + "}";
      try {
        GitCloneJson json = (GitCloneJson) Json.fromJson(obj, GitCloneJson.class, true);
        if ("file".equals(json.type)) {
            String destino = new File(Dir,json.name).getAbsolutePath();
            gitclone.GitClone(json.download_url,destino);
        } 

      } catch (Exception e) {
      }
    }
  }

  public static class GitCloneJson {
    public String name;
    public String download_url;
    public String type;
    public String sha;
  }
}
