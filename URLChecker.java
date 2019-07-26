import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class URLChecker {

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("Usage)");
			System.out.println("  'java URLCheck google.com' or ");
			System.out.println("  'java URLCheck google.com verbose' to see body");
			System.out.println("Please give me at least a host name(ex, google.com)!");
			System.exit(-1);
		}

		String hostName = args[0];
		String verbose = null;
		if (args.length >= 2) {
			verbose = args[1];
		}

		URLConnection urlConnection = new URL("http://" + hostName).openConnection();
		urlConnection.setRequestProperty("Accept", "text/html");

		try (Scanner scanner = new Scanner(urlConnection.getInputStream())) {
			printResponseHeaders(urlConnection);
			if (verbose != null && verbose.equals("verbose")) {
				printResponseBody(scanner);
			}
		}
	}

	private static void printResponseHeaders(URLConnection urlConnection) {
		System.out.println("[Header]");
		for (Map.Entry<String, List<String>> header : urlConnection.getHeaderFields().entrySet()) {
			if (header.getKey() == null) {
				System.out.println(header.getValue().get(0));
				continue;
			}
			System.out.printf("  %s: %s%n", header.getKey(), header.getValue());
		}
	}

	private static void printResponseBody(Scanner scanner) {
		System.out.println("[Body]");
		while (scanner.hasNextLine()) {
			System.out.println(scanner.nextLine());
		}
	}
}

