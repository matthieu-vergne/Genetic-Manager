package sample.TSP;

import java.io.FileInputStream;
import java.io.StringWriter;

public class CsvReader {

	public static Location[] parse(String filepath) {
		try {
			Location[] locations = null;
			FileInputStream fis = new FileInputStream(filepath);
			StringWriter writer = new StringWriter();
			int c;
			int index = 0;
			while ((c = fis.read()) != -1) {
				if (c == '\n') {
					writer.flush();
					String buffer = writer.getBuffer().toString();
					writer.getBuffer().delete(0, writer.getBuffer().length());
					if (locations == null) {
						locations = new Location[Integer.parseInt(buffer)];
					} else {
						int sepIndex = buffer.indexOf(";");
						double x = Double.parseDouble(buffer.substring(0,
								sepIndex));
						double y = Double.parseDouble(buffer
								.substring(sepIndex + 1));
						locations[index] = new Location(x, y);
						index++;
					}
				} else {
					writer.write(c);
				}
			}

			return locations;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
