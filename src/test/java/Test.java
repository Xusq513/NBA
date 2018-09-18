/**
 * @Auther: Michael Xu
 * @Date: 2018/9/17 10:41
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        String url = "http://www.stat-nba.com/award/item11isnba1season2003.html";
        String[] array = url.split("/");
        String lastStr = array[array.length - 1];
        lastStr = lastStr.replaceAll("item11isnba1season","").replaceAll("\\.html","");
        System.out.println(lastStr);
    }


}
