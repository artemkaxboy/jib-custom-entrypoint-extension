// The most simple example of application

public class Application {

  public static void main(String[] args) {
    String argString = String.join(", ", args);
    String outString = argString.isEmpty() ? "sample-maven-java" : argString;
    System.out.println("Hello from " + outString);
  }
}
