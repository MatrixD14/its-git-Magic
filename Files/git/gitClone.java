public class gitClone {
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
}
