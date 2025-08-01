public class gitPushMult {
  public void UpVariaPasth(gitPush gitpush, File dir, String base, String linkNamePasth,String Commit, String pasth, String toke) {
    File[] file = dir.listFiles();
    if (file == null) return;
    for (File f : file) {
      if (f.isDirectory()) UpVariaPasth(gitpush, f, base, linkNamePasth,Commit, pasth, toke);
      else if (!f.getName().startsWith(".")) {

        String name = f.getAbsolutePath().replace(base, "");
        String cominho = f.getAbsolutePath();
        String API_Url = "https://api.github.com/repos/" + linkNamePasth + "/contents/Files/" + pasth + name + "?ref=main";

        // busca o sha do file

        String shas = gitpush.getSha(API_Url, toke);
        gitpush.GitPush(API_Url, Commit, cominho, toke, shas);

        Console.log(!shas.isEmpty() ? "update" : "create");
        Console.log("Link: " + API_Url);
      }
    } 
  }
}
