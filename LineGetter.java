import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class LineGetter {

	/**
	 * Get the N-th line from a file and. If the requested line number exceeds the line count in the file, throw an exception.
	 * @param fileName This must include the path. Example: ./src/employees.txt
	 * @param lineNum
	 * @return string representation of a line in the file
	 * @throws Exception If the N is greater than the line count in the file.
	 */
	public String getLineFromFile(String fileName, long lineNum) throws Exception {
		Path path = Paths.get(fileName);
		
		String nthLine = null;
		try (Stream<String> lines = Files.lines(path)) {

			nthLine = lines.skip(lineNum).findFirst().get();
			
		} catch (IOException | NoSuchElementException ex) {
			// I know it's not quite smooth, but I wanted to show usage of '|' 
			if (nthLine == null) {
				throw new Exception("The requested line number exceeds the line count in the file.");  
			}
		}
		return nthLine;
	}
	
	
	public static void main(String[] args) {
		// File name must include the path
		String line;
		try {
			line = new LineGetter().getLineFromFile("./src/employees.db", 3);
		} catch (Exception e) {
			System.out.println("Error reading line from the file: " + e.getMessage());
			return;
		}
		System.out.println(line);
	}

}
