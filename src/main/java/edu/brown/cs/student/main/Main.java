package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      StarBot starBot = new StarBot(""); // initialize null StarBot
      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          String[] arguments = input.split(" ");
          // handling math cases first
          if (arguments[0].equals("add") || arguments[0].equals("subtract")) {
            MathBot mathBot = new MathBot();
            if (arguments[0].equals("add") && arguments.length == 3) {
              System.out.println(mathBot.add(Integer.parseInt(arguments[1]),
                  Integer.parseInt(arguments[2])));
            } else if (arguments[0].equals("subtract") && arguments.length == 3) {
              System.out.println(mathBot.subtract(Integer.parseInt(arguments[1]),
                  Integer.parseInt(arguments[2])));
            } else {
              throw new Exception("ERROR: Invalid operation");
            }
          // handle stars
          } else if (arguments[0].equals("stars") || arguments[0].equals("naive_neighbors")) {
            if (arguments[0].equals("stars") && arguments.length == 2) { // generate bot
              starBot = new StarBot(arguments[1]);
              System.out.println("Read " + starBot.getSize() + " stars from " + arguments[1]);
            // search for star case
            } else if (arguments[0].equals("naive_neighbors") && arguments.length == 3) {
              Star starI = starBot.searchCoords(arguments[2].replaceAll("^\"|\"$", ""));
              System.out.println("TESTING|" + starI.getName());
              ArrayList<Star> sortedStars = starBot.updateSortDist(starI.getX(), starI.getY(), starI.getZ());
              for (int i = 1; i < Math.min(Integer.parseInt(arguments[1]) + 1, starBot.getSize() + 1); i++) {
                System.out.println(sortedStars.get(i).getID());
              }
            // coordinate input case
            } else if (arguments[0].equals("naive_neighbors") && arguments.length == 5) {
              ArrayList<Star> sortedStars = starBot.updateSortDist(Double.parseDouble(arguments[2]),
                  Double.parseDouble(arguments[3]), Double.parseDouble(arguments[4]));
              for (int i = 0; i < Math.min(Integer.parseInt(arguments[1]), starBot.getSize()); i++) {
                System.out.println(sortedStars.get(i).getID());
              }
            } else {
              throw new NoSuchFieldException("");
            }
          } else {
            throw new NoSuchFieldException("");
          }
        } catch (Exception e) {
          e.printStackTrace();
          if (e instanceof FileNotFoundException) {
            System.out.println("ERROR: incorrect filepath");
          }
          if (e instanceof NoSuchFieldException) {
            System.out.println("ERROR: incorrect command arguments");
          }
        }
      }
    } catch (Exception e) {
      System.out.println("ERROR: Invalid input for REPL");
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
