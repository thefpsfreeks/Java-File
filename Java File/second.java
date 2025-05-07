import java.util.StringTokenizer;
public class second {
    public static void main(String[] args) {
        String str = "Java is a versatile and powerful programming language.";

        StringTokenizer tokenizer = new StringTokenizer(str);
        System.out.println("Tokens from the input string:");
        
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken());
        }   
    }
}
